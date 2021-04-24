package org.decembrist.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class FrontendApp {

    public static void main(String[] args) {
        SpringApplication.run(FrontendApp.class);
    }

}

@Configuration
class FrontendConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

@RestController
class FrontendController {

    private RestTemplate restTemplate;

    public FrontendController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/backend/ping")
    public String pong() {
        return restTemplate.getForObject("http://localhost:8082/ping", String.class);
    }

}
