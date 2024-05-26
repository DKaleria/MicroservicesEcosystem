package by.javaguru.prroductservice.config;

import by.javaguru.prroductservice.service.ProductService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public ProductService productService(RestTemplate restTemplate) {
        return new ProductService(restTemplate);
    }
}