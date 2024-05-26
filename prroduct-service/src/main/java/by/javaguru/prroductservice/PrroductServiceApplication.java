package by.javaguru.prroductservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PrroductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrroductServiceApplication.class, args);
    }

}
