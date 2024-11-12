package by.javaguru.auth_userservice.controllers;

import by.javaguru.auth_userservice.database.entity.User;
import by.javaguru.auth_userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth_users")
public class AuthUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> searchUsers(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        List<User> users = userService.searchUsers(token);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{user_id}")
    public Optional<User> findById(@PathVariable("user_id") Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Object currentUser = userService.getCurrentUser(token);
        return ResponseEntity.ok(currentUser);
    }

}
