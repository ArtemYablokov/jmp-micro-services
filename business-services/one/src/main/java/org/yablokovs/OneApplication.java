package org.yablokovs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController("/business")
public class OneApplication {

    @Autowired
    ServletWebServerApplicationContext webServerApplicationContext;

    public static void main(String[] args) {
        SpringApplication.run(OneApplication.class, args);
    }

    @GetMapping("/one")
    public ResponseEntity<String> getOne() {
        int port = webServerApplicationContext.getWebServer().getPort();
        return ResponseEntity.ok(String.format("This is One-service on port \"%s\"!", port));
    }

}
