package com.example.datn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DatnApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatnApplication.class, args);
        System.out.println("Running...");
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String rawPassword = "12345";
//        String encodedPassword = encoder.encode(rawPassword);
//        System.out.println(encodedPassword);


    }

}
