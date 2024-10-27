package by.javaguru.identityservice.service;

import by.javaguru.identityservice.database.entity.CustomUserDetails;
import by.javaguru.identityservice.database.entity.RegistrationRequest;
import by.javaguru.identityservice.database.entity.User;
import by.javaguru.identityservice.database.repository.UserRepository;
import by.javaguru.identityservice.usecaseses.events.AuthUserGotEvent;
import by.javaguru.identityservice.usecaseses.mapper.AuthUserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final AuthUserMapper authUserMapper;
    private KafkaTemplate<String, AuthUserGotEvent> kafkaTemplate;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthUserMapper authUserMapper, KafkaTemplate<String, AuthUserGotEvent> kafkaTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authUserMapper = authUserMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public User register(RegistrationRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .birthDate(request.getBirthDate())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

    public AuthUserGotEvent getCurrentUser() throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            String userId = user.getId().toString();
            AuthUserGotEvent authUserGotEvent = authUserMapper.userToAuthUserGotEvent(user);
            SendResult<String, AuthUserGotEvent> result =
                    kafkaTemplate.send("auth-user-got-events-topic",
                            userId,
                            authUserGotEvent).get();

            LOGGER.info("Topic: {}", result.getRecordMetadata().topic());
            LOGGER.info("Partition UserGetEvent: {}", result.getRecordMetadata().partition());
            LOGGER.info("Return result: {}", authUserGotEvent);

            return authUserGotEvent;
        }
        throw new RuntimeException("Authentication is not valid");
    }
}