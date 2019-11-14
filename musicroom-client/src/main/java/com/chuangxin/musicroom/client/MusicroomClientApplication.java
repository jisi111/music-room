package com.chuangxin.musicroom.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.chuangxin.musicroom"})
public class MusicroomClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicroomClientApplication.class, args);
    }

}
