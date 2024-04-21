package com.kairosgames.kairos_games.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry){
        registry.addMapping("/api/")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                .exposedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);

        registry.addMapping("/api/games")
            .allowedOrigins("http://localhost:5173")
            .allowedMethods("GET")
            .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
            .exposedHeaders("*")
            .allowCredentials(false)
            .maxAge(3600);

        registry.addMapping("/password/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("*")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                .exposedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);


        registry.addMapping("/auth/**")
            .allowedOrigins("http://localhost:5173")
            .allowedMethods("GET", "POST")
            .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
            .allowCredentials(false)
            .maxAge(3600);
            
            registry.addMapping("/api/users/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("*")
                .allowedHeaders("Origin","Accept", "Access-Control-Allow-Origin", "Content-Type", "Authorization")
                .exposedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);

        registry.addMapping("/api/users")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("*")
                .allowedHeaders("Origin","Accept", "Access-Control-Allow-Origin", "Content-Type", "Authorization")
                .exposedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);

        registry.addMapping("/api/user/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("*")
                .allowedHeaders("Origin","Accept", "Access-Control-Allow-Origin", "Content-Type", "Authorization")
                .exposedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);

    }

    
}
