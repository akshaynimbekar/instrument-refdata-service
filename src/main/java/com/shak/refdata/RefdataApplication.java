package com.shak.refdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class RefdataApplication {
    public static void main(String[] args) {
        SpringApplication.run(RefdataApplication.class, args);
    }
}
