package by.javaguru.userservice.service.impl;

import by.javaguru.userservice.entity.User;
import by.javaguru.userservice.service.UserService;
import by.javaguru.userservice.service.event.UserGetEvent;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserServiceImpl implements UserService {
    private KafkaTemplate<Long, UserGetEvent> kafkaTemplate;
    private final Map<Long, User> userDatabase = new HashMap<>();
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public UserServiceImpl(KafkaTemplate<Long, UserGetEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    private void initializeProductDatabase() {
        userDatabase.put(4L, new User(4L, "Vladimir"));
        userDatabase.put(5L, new User(5L, "Alex"));
    }

    public UserGetEvent getUserById(Long id) throws ExecutionException, InterruptedException {
        User user = userDatabase.get(id);
        UserGetEvent userGetEvent = new UserGetEvent(user.getId(), user.getName());
        SendResult<Long, UserGetEvent> result =
                kafkaTemplate.send("user-get-events-topic", id, userGetEvent).get();
        LOGGER.info("Topic: {}", result.getRecordMetadata().topic());
        LOGGER.info("Partition UserGetEvent: {}", result.getRecordMetadata().partition());

        LOGGER.info("Return result: {}", userGetEvent);
        return userGetEvent;
    }
}