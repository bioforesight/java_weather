package com.example.weather.controller;

import com.example.weather.model.Weather;
import com.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public Iterable<Weather> findAll() {
        return weatherService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Weather> findById(@PathVariable int id) {
        Optional<Weather> weather = weatherService.findById(id);
        return weather.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/coordinates")
    public ResponseEntity<Weather> findByCoordinates(@RequestParam double latitude, @RequestParam double longitude) {
        Optional<Weather> weather = weatherService.findByCoordinates(latitude, longitude);
        return weather.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Weather> save(@RequestBody Weather weather) {
        if (weatherService.existsById(weather.getId())) {
            return new ResponseEntity<>(weatherService.findById(weather.getId()).get(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(weatherService.save(weather), HttpStatus.CREATED);
        }
    }
}