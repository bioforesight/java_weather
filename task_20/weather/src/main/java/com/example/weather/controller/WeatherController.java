package com.example.weather.controller;

import com.example.weather.model.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@Component
public class WeatherController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${appid}")
    private String appId;

    @Value("${url.weather}")
    private String urlWeather;

    private final ConcurrentMap<String, Root> cache = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = 60000)
    public void updateCache() {
        for (String key : cache.keySet()) {
            String[] latLon = key.split("-");
            String lat = latLon[0];
            String lon = latLon[1];
            String request = String.format("%s?lat=%s&lon=%s&units=metric&appid=%s", urlWeather, lat, lon, appId);
            Root weatherData = restTemplate.getForObject(request, Root.class);
            cache.put(key, weatherData);
        }
    }

    @GetMapping("/weather")
    public Root getWeather(@RequestParam String lat, @RequestParam String lon) {
        String key = lat + "-" + lon;
        if (!cache.containsKey(key)) {
            String request = String.format("%s?lat=%s&lon=%s&units=metric&appid=%s", urlWeather, lat, lon, appId);
            Root weatherData = restTemplate.getForObject(request, Root.class);
            cache.put(key, weatherData);
        }
        return cache.get(key);
    }
}