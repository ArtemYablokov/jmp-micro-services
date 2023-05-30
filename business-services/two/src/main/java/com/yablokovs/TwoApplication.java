package com.yablokovs;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/business")
@EnableFeignClients
public class TwoApplication {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    Client client;

    @Autowired
    EurekaClient eurekaClient;

    @Autowired
    ServletWebServerApplicationContext webServerApplicationContext;

    public static void main(String[] args) {
        SpringApplication.run(TwoApplication.class, args);
    }

    @GetMapping("/two")
    public ResponseEntity<String> getOne() {
        String oneResponse = client.getOne();

        String name = eurekaClient.getApplication(appName).getName();
        int port = webServerApplicationContext.getWebServer().getPort();
        return ResponseEntity.ok(String.format(
                "This is Two-service on port \"%s\"! With name \"%s\"!    Recieved from ONE-APPLICATION: \" %s \"",
                port, name, oneResponse));
    }

}
