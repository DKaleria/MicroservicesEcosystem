package by.javaguru.auth_userservice.handler;

import by.javaguru.auth_userservice.events.AuthUserGotEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "auth-user-got-events-topic")
public class AuthUserGotEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(AuthUserGotEvent authUserGotEvent){
        LOGGER.info("Received event: {}", authUserGotEvent.toString());
    }
    @KafkaHandler(isDefault = true)
    public void handleDefault(Message<?> message) {
        LOGGER.warn("Received unknown message type: {}", message);
    }
}
