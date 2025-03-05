package com.coreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.coreapi.config", "com.coreapi.controllers", "com.coreapi.models"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}