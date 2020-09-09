package dev.boiarshinov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class SpringEmailApp {
    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(SpringEmailApp.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        app.run(args);
    }
}
