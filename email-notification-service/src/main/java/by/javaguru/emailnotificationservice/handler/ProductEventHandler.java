package by.javaguru.emailnotificationservice.handler;

import by.javaguru.core.event.ProductCreatedEvent;
import by.javaguru.core.event.ProductGetEvent;
import by.javaguru.emailnotificationservice.database.entity.ProcessedEventEntity;
import by.javaguru.emailnotificationservice.database.entity.ProductGetEventEntity;
import by.javaguru.emailnotificationservice.database.repository.ProcessedEventRepository;
import by.javaguru.emailnotificationservice.database.repository.ProductGetEventRepository;
import by.javaguru.emailnotificationservice.exception.NonRetryableException;
import by.javaguru.emailnotificationservice.exception.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@KafkaListener(topics = {"product-created-events-topic", "product-get-events-topic"})
public class ProductEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private RestTemplate restTemplate;
    private ProcessedEventRepository processedEventRepository;
    private ProductGetEventRepository productGetEventRepository;

    public ProductEventHandler(RestTemplate restTemplate, ProcessedEventRepository processedEventRepository,
                               ProductGetEventRepository productGetEventRepository) {
        this.restTemplate = restTemplate;
        this.processedEventRepository = processedEventRepository;
        this.productGetEventRepository = productGetEventRepository;
    }

    @Transactional
    @KafkaHandler
    public void handleProductCreatedEvent(@Payload ProductCreatedEvent productCreatedEvent,
                                          @Header("messageId") String messageId,
                                          @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {
        LOGGER.info("Received event: {}", productCreatedEvent.getTitle());
        ProcessedEventEntity processedEventEntity = processedEventRepository.findByMessageId(messageId);

        if (processedEventEntity != null) {
            LOGGER.info("Duplicate messageID: {}", messageId);
            return;
        }
        try {
            String url = "http://localhost:8090/response/200";

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET,
                    null,
                    String.class);
            if (response.getStatusCode().value() == HttpStatus.OK.value()) {
                LOGGER.info("Received response: {}", response.getBody());
            }
        } catch (ResourceAccessException e) {
            LOGGER.error(e.getMessage());
            throw new RetryableException(e);
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
            throw new NonRetryableException(e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new NonRetryableException(e);
        }
        try {
            processedEventRepository.save(new ProcessedEventEntity(messageId, productCreatedEvent.getId()));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.getMessage());
            throw new NonRetryableException(e);
        }
    }

    @Transactional
    @KafkaHandler
    public void handleProductGetEvent(
            @Payload ProductGetEvent productGetEvent
    ) {
        LOGGER.info("Received event 2: {}", productGetEvent.getTitle());
        productGetEventRepository.save(new ProductGetEventEntity(
                productGetEvent.getTitle(), productGetEvent.getPrice(), productGetEvent.getId()));
    }
}