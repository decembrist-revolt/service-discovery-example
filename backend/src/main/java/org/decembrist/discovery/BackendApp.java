package org.decembrist.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
public class BackendApp {

    public static void main(String[] args) {
        SpringApplication.run(BackendApp.class);
    }

}

@RestController
class BackendLogicController {

    private ServiceInstance serviceInstance;

    BackendLogicController(ServiceInstance serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    @GetMapping("/ping")
    public String pong() {
        return "pong " + serviceInstance.getInstanceId();
    }

}
