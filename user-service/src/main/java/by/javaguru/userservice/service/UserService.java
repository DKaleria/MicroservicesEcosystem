package by.javaguru.userservice.service;

import by.javaguru.userservice.dto.UserDto;
import by.javaguru.userservice.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<Long, User> userDatabase = new HashMap<>();

    @PostConstruct
    private void initializeProductDatabase() {
        userDatabase.put(4L, new User(4L, "Vladimir"));
        userDatabase.put(5L, new User(5L, "Alex"));
    }

    public UserDto getUserById(Long id) {
        User user = userDatabase.get(id);
        return new UserDto(user.getId(), user.getName());
    }
}