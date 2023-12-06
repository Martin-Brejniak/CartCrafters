package com.example.auctionserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CartCraftersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartCraftersApplication.class, args);
    }
}
