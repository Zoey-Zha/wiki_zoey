package com.zoey;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zoey.mapper")
@EnableScheduling
@EnableAsync
public class WikiZoeyApplication {

    public static void main(String[] args) {

        SpringApplication.run(WikiZoeyApplication.class, args);
    }

}
