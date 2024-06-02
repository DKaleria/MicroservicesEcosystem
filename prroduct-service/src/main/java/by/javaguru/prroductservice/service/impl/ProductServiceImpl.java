package by.javaguru.prroductservice.service.impl;

import by.javaguru.core.dto.UserDto;
import by.javaguru.core.event.ProductCreatedEvent;
import by.javaguru.prroductservice.client.UserServiceFeignClient;
import by.javaguru.prroductservice.dto.ProductDto;
import by.javaguru.prroductservice.entity.Product;
import by.javaguru.prroductservice.service.ProductService;
import by.javaguru.prroductservice.service.event.ProductGetEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@Transactional(value = "kafkaTransactionManager")
public class ProductServiceImpl implements ProductService {
    private KafkaTemplate<String, ProductCreatedEvent> productCreatedEventKafkaTemplate;
    private KafkaTemplate<String, ProductGetEvent> productGetEventKafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<String, Product> productDatabase = new HashMap<>();
    private final UserServiceFeignClient userServiceFeignClient;
// private final RestTemplate restTemplate;
// private final String usersApiUrl;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate, KafkaTemplate<String, ProductGetEvent> productGetEventKafkaTemplate, UserServiceFeignClient userServiceFeignClient) {
        this.productCreatedEventKafkaTemplate = kafkaTemplate;
        this.productGetEventKafkaTemplate = productGetEventKafkaTemplate;
        this.userServiceFeignClient = userServiceFeignClient;
// this.restTemplate = restTemplate;
// this.usersApiUrl = AppConstants.USERS_API_URL;
        initializeProductDatabase();
    }

    private void initializeProductDatabase() {
        productDatabase.put("a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6", new Product("a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6", "Product 1", new BigDecimal("19.99"),
                5, 4L));
        productDatabase.put("7d8a9b0c-1e2f-3g4h-5i6j-7k8l9m0n1p2", new Product("7d8a9b0c-1e2f-3g4h-5i6j-7k8l9m0n1p2", "Product 2", new BigDecimal("45.01"),
                3, 5L));
    }

    public ProductGetEvent getProductById(String id) throws ExecutionException, InterruptedException {
        Product product = productDatabase.get(id);
        UserDto user = getUserById(product.getUserId());
        ProductGetEvent productGetEvent = new ProductGetEvent(product.getId(), product.getTitle(), product.getPrice(), product.getQuantity(), user);
        SendResult<String, ProductGetEvent> result =
                productGetEventKafkaTemplate.send("product-get-events-topic", id, productGetEvent).get();

        logger.info("Partition ProductGetEvent: {}", result.getRecordMetadata().partition());

        logger.info("Return result: {}", productGetEvent);
        return productGetEvent;
    }

    private UserDto getUserById(Long userId) {
// String url = usersApiUrl + "/users/{id}";
//
// String url = "http://localhost:8082/user-service/users/{id}";
// UserDto userDto = restTemplate.getForObject(url, UserDto.class, userId);
// return userDto;
        return userServiceFeignClient.getUserById(userId);
    }

    @Override
    public String createdProduct(ProductDto productDto) throws ExecutionException, InterruptedException {
        String productId = UUID.randomUUID().toString();
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
                productDto.getTitle(), productDto.getPrice(), productDto.getQuantity(), productDto.getUser());

        ProducerRecord<String, ProductCreatedEvent> record = new ProducerRecord<>(
                "product-created-events-topic",
                productId,
                productCreatedEvent
        );
        record.headers().add("messageId", UUID.randomUUID().toString().getBytes());

        SendResult<String, ProductCreatedEvent> result =
                productCreatedEventKafkaTemplate.send(record).get();

        logger.info("Topic: {}", result.getRecordMetadata().topic());
        logger.info("Partition: {}", result.getRecordMetadata().partition());
        logger.info("Offset: {}", result.getRecordMetadata().offset());

        logger.info("Return: {}", productId);

        return productId;
    }
}