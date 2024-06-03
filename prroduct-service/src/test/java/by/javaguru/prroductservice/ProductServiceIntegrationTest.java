package by.javaguru.prroductservice;

import by.javaguru.core.dto.UserDto;
import by.javaguru.core.event.ProductCreatedEvent;
import by.javaguru.prroductservice.dto.ProductDto;
import by.javaguru.prroductservice.service.ProductService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EmbeddedKafka(partitions = 3, count = 3, controlledShutdown = true)
@ActiveProfiles("test")
@SpringBootTest(properties = "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}")
public class ProductServiceIntegrationTest {
    @Autowired
    ProductService productService;
    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    Environment environment;

    private KafkaMessageListenerContainer<String, ProductCreatedEvent> container;
    private BlockingQueue<ConsumerRecord<String, ProductCreatedEvent>> records;

    @BeforeAll
    void setup() {
        DefaultKafkaConsumerFactory<String, Object> consumerFactory =
                new DefaultKafkaConsumerFactory<>(getConsumerProperties());
        ContainerProperties containerProperties = new ContainerProperties(
                environment.getProperty("product-created-events-topic-name"));
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<String, ProductCreatedEvent>) records::add);
        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
    }

    @Test
    void testCreateProductSuccessfully() throws Exception {
        //Arrange
        String id = "7d4c5f4a-1b6a-43d0-9f5e-42b7cabd7a56";
        String title = "Product A";
        BigDecimal price = new BigDecimal("19.99");
        Integer quantity = 10;
        UserDto userDto = new UserDto(10L, "Marina");

        ProductDto createProductDto = new ProductDto(id, title, price, quantity, userDto);

        //Act
        productService.createdProduct(createProductDto);

        //Assert
        ConsumerRecord<String, ProductCreatedEvent> message = records.poll(3000, TimeUnit.MILLISECONDS);
        assertNotNull(message);
        assertNotNull(message.key());
        ProductCreatedEvent productCreatedEvent = message.value();
        assertEquals(createProductDto.getQuantity(), productCreatedEvent.getQuantity());
        assertEquals(createProductDto.getTitle(), productCreatedEvent.getTitle());
        assertEquals(createProductDto.getId(), productCreatedEvent.getId());
        assertEquals(createProductDto.getUser(), productCreatedEvent.getUser());
        assertEquals(createProductDto.getPrice(), productCreatedEvent.getPrice());
    }

    public Map<String, Object> getConsumerProperties() {
        return Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
                ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class,
                JsonDeserializer.TRUSTED_PACKAGES, environment.getProperty("spring.kafka.consumer.properties.spring.json.trusted.packages"),
                ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.group-id"),
                ConsumerConfig.ISOLATION_LEVEL_CONFIG, environment.getProperty(
                        "spring.kafka.consumer.isolation-level", "READ_COMMIT"
                ).toLowerCase(),
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, environment.getProperty("spring.kafka.consumer.auto-offset-reset")
        );
    }

    @AfterAll
    void tearDown() {
        container.stop();
    }
}
