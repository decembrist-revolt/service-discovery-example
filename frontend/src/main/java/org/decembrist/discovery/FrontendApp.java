package org.decembrist.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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

    public static final String BACKEND_SERVICE_ID = "backend";

    private final RestTemplate restTemplate;
    private final DiscoveryClient client;
    private final LoadBalancerClient loadBalancer;

    public FrontendController(RestTemplate restTemplate,
                              DiscoveryClient client,
                              LoadBalancerClient loadBalancer) {
        this.restTemplate = restTemplate;
        this.client = client;
        this.loadBalancer = loadBalancer;
    }

    @GetMapping("/backend/ping")
    public String pong() throws IOException {
        return loadBalancer.execute(BACKEND_SERVICE_ID, backendInstance -> {
            URI backendUrl = backendInstance.getUri().resolve("/ping");
            String pong = restTemplate.getForObject(backendUrl, String.class);
            return pong + " " + backendInstance.getInstanceId();
        });
    }

}
