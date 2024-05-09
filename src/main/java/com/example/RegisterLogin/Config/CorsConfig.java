package com.example.RegisterLogin.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Or the origin of your frontend application
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Or the methods allowed by your endpoints
                .allowedHeaders("*") // Or specify the headers required by your requests
                .allowCredentials(true); // If your requests include credentials (e.g., cookies)
    }
}