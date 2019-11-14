package com.chuangxin.musicroom.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.chuangxin.musicroom"})
public class MusicroomAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicroomAdminApplication.class, args);
    }

}
