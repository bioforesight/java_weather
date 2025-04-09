package com.example.weather_app.weather.repository;

import com.example.weather_app.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    Optional<Weather> findByLatitudeAndLongitude(double latitude, double longitude);
}