package by.javaguru.auth_userservice.service.impl;

import by.javaguru.auth_userservice.database.entity.User;
import by.javaguru.auth_userservice.database.repository.UserRepository;
import by.javaguru.auth_userservice.service.UserService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public UserServiceImpl(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    @Override
    public List<User> searchUsers(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        String url = "http://identity-service:8088/identity/users";
        ResponseEntity<List<User>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<User>>() {}
        );

        List<User> usersFromService = response.getBody();
        if (usersFromService != null) {
            for (User user : usersFromService) {
                if (!userRepository.findByUsername(user.getUsername()).isPresent()) {
                    userRepository.save(user);
                }
            }
        }

        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getCurrentUser(String token) {
        String url = "http://identity-service:8088/identity/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Object> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Object>() {}
        );

        return response.getBody();
    }

}