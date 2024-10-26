package by.javaguru.identityservice.controller;

import by.javaguru.identityservice.database.entity.*;
import by.javaguru.identityservice.exceptions.ErrorMessage;
import by.javaguru.identityservice.service.UserService;
import by.javaguru.identityservice.utils.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@Transactional
@RequestMapping("/identity")
public class AuthController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegistrationRequest request) {
        User user = userService.register(request);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/users")
    @Transactional(readOnly = true)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @SneakyThrows
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.isAuthenticated()) {
            String jwt = jwtTokenUtil.generateToken(authentication);
            System.out.println("Generated token: " + jwt);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }else {
            throw new RuntimeException("invalid access");
        }
    }

    @PostMapping("/me")
    public ResponseEntity<Object> getCurrentUser(@RequestBody AuthenticationRequest authRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.getCurrentUser());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(new Date(),e.getMessage()));
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Void> validateToken(@RequestParam("Authorization") String authorization) {
        if (jwtTokenUtil.validateToken(authorization)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}