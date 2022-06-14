package com.sekretowicz.alfabanktask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AlfaBankTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlfaBankTaskApplication.class, args);
    }
}
