package org.yablokovs;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RequestMapping("/business")
public class OneApplication {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    EurekaClient eurekaClient;

    @Autowired
    ServletWebServerApplicationContext webServerApplicationContext;

    public static void main(String[] args) {
        SpringApplication.run(OneApplication.class, args);
    }

    @GetMapping("/one")
    public ResponseEntity<String> getOne() {
        String name = eurekaClient.getApplication(appName).getName();
        int port = webServerApplicationContext.getWebServer().getPort();
        return ResponseEntity.ok(String.format("This is One-service on port \"%s\"! With name \"%s\"!", port, name));
    }

}
