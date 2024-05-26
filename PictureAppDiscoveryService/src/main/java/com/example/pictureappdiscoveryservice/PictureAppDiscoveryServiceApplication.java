package com.example.pictureappdiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PictureAppDiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureAppDiscoveryServiceApplication.class, args);
    }

}
