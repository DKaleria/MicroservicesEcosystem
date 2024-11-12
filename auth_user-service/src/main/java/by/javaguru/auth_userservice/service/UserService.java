package by.javaguru.auth_userservice.service;

import by.javaguru.auth_userservice.database.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);
    List<User> searchUsers(String token);
    Object getCurrentUser(String token);
}