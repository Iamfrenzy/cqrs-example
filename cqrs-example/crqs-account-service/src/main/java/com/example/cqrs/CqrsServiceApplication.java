package com.example.cqrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.example.cqrs"})
public class CqrsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CqrsServiceApplication.class, args);
    }
}
