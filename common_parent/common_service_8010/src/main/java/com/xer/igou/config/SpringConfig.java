package com.xer.igou.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration
public class SpringConfig {
    @Bean
    MultipartConfigElement multipartConfigElement() {
        File file = new File("C:\\Users\\xer\\AppData\\Local\\Temp\\");
        file.setWritable(true, false);
        if (!file.exists()) {
            file.mkdirs();
        }
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("C:\\Users\\xer\\AppData\\Local\\Temp\\");
        return factory.createMultipartConfig();
    }
}
