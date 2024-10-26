package by.javaguru.auth_userservice.controllers;

import by.javaguru.auth_userservice.database.entity.User;
import by.javaguru.auth_userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth_users")
public class AuthUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public Optional<User> findByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/{user_id}")
    public Optional<User> findById(@PathVariable("user_id") Long userId) {
        return userService.findById(userId);
    }

//    @GetMapping("/me")
//    public User getCurrentUser() {
//        return userService.getCurrentUser();
//    }

}
