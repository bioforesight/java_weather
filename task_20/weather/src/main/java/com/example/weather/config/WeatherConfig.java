package com.example.weather.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCaching // Enable caching
public class WeatherConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}