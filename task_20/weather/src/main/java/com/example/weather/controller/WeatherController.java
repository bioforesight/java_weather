package com.example.weather.controller;

import com.example.weather.model.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${appid}")
    private String appId;

    @GetMapping("/weather")
    @Cacheable(value = "weather", key = "#lat + '_' + #lon")
    public Root getWeather(@RequestParam("lat") double lat, @RequestParam("lon") double lon) {
        System.out.println("Fetching weather data from API for lat: " + lat + ", lon: " + lon);
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=metric&appid=%s", lat, lon, appId);
        return restTemplate.getForObject(url, Root.class);
    }
}