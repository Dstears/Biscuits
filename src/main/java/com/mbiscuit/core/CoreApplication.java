package com.mbiscuit.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.additional-location", "file:${user.home}/mbiscuit/");
        SpringApplication.run(CoreApplication.class, args);
    }

}
