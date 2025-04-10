package com.example.weather.service;

import com.example.weather.model.Weather;
import com.example.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository repository;

    public Iterable<Weather> findAll() {
        return repository.findAll();
    }

    public Optional<Weather> findById(int id) {
        return repository.findById(id);
    }

    public Optional<Weather> findByCoordinates(double latitude, double longitude) {
        return repository.findByLatitudeAndLongitude(latitude, longitude);
    }

    public Weather save(Weather weather) {
        return repository.save(weather);
    }

    public boolean existsById(int id) {
        return repository.existsById(id);
    }
}