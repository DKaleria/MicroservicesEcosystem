package by.javaguru.userservice.controller;

import by.javaguru.userservice.exception.ErrorMessage;
import by.javaguru.userservice.service.UserService;
import by.javaguru.userservice.service.event.UserGetEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
        UserGetEvent user = null;
        try {
           user = userService.getUserById(id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(new Date(), e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
