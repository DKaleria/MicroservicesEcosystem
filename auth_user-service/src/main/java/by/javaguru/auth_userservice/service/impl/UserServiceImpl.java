package by.javaguru.auth_userservice.service.impl;

import by.javaguru.auth_userservice.database.entity.User;
import by.javaguru.auth_userservice.database.repository.UserRepository;
import by.javaguru.auth_userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findUserById(id);
    }
}
