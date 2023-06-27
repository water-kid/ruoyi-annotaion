package com.cj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.cj.mapper")
public class RuoyiAnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuoyiAnnotationApplication.class, args);
    }

}
