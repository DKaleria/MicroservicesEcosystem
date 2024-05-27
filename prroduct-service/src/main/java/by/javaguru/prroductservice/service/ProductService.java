package by.javaguru.prroductservice.service;

import by.javaguru.prroductservice.client.UserServiceFeignClient;
import by.javaguru.prroductservice.constants.AppConstants;
import by.javaguru.prroductservice.dto.ProductDto;
import by.javaguru.prroductservice.entity.Product;
import by.javaguru.userservice.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {
    private final Map<Long, Product> productDatabase = new HashMap<>();
    private final UserServiceFeignClient userServiceFeignClient;
//    private final RestTemplate restTemplate;
//    private final String usersApiUrl;

    public ProductService(RestTemplate restTemplate, UserServiceFeignClient userServiceFeignClient) {
        this.userServiceFeignClient = userServiceFeignClient;
//        this.restTemplate = restTemplate;
//        this.usersApiUrl = AppConstants.USERS_API_URL;

        initializeProductDatabase();
    }

    private void initializeProductDatabase() {
        productDatabase.put(1L, new Product(1L, "Product 1", 4L));
        productDatabase.put(2L, new Product(2L, "Product 2", 5L));
    }

    public ProductDto getProductById(Long id) {
        Product product = productDatabase.get(id);
        UserDto user = getUserById(product.getUserId());
        return new ProductDto(product.getId(), product.getName(), user);
    }

    private UserDto getUserById(Long userId) {
//        String url = usersApiUrl + "/users/{id}";
//
//        String url = "http://localhost:8082/user-service/users/{id}";
//        UserDto userDto = restTemplate.getForObject(url, UserDto.class, userId);
//        return userDto;

        return userServiceFeignClient.getUserById(userId);
    }
}
