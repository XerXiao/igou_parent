package com.xer.igou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProductServerApplication_8002 {
    public static void main(String[] args) {
        SpringApplication.run(ProductServerApplication_8002.class);
    }
}
