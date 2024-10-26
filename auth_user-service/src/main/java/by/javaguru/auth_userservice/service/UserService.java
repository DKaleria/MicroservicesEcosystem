package by.javaguru.auth_userservice.service;

import by.javaguru.auth_userservice.database.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
//    User getCurrentUser();
    Optional<User> findById(Long id);

}
