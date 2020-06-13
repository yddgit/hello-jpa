package com.my.project.jpa.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.my.project.jpa.rest")
public class RestJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestJpaApplication.class, args);
    }

}
