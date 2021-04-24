package org.decembrist.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

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

    private final RestTemplate restTemplate;
    private final DiscoveryClient client;

    public FrontendController(RestTemplate restTemplate,
                              DiscoveryClient client) {
        this.restTemplate = restTemplate;
        this.client = client;
    }

    @GetMapping("/backend/ping")
    public String pong() {
        ServiceInstance backendInstance = client.getInstances("backend").get(0);
        URI backendUrl = backendInstance.getUri().resolve("/ping");
        String pong = restTemplate.getForObject(backendUrl, String.class);
        return pong + " " + backendInstance.getInstanceId();
    }

}
