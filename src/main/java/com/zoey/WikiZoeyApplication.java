package com.zoey;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zoey.mapper")
public class WikiZoeyApplication {

    public static void main(String[] args) {

        SpringApplication.run(WikiZoeyApplication.class, args);
    }

}
