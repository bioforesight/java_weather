package com.example.location.controller;

import com.example.location.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.location.model.Location;
import com.example.location.repository.LocationRepository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/weather")
    public Weather redirectRequestWeather(@RequestParam String name) {
        Optional<Location> locationOpt = repository.findByName(name);
        if (locationOpt.isPresent()) {
            Location location = locationOpt.get();
            String url = String.format("http://localhost:8083/?lat=%s&lon=%s", location.getLatitude(), location.getLongitude());
            return restTemplate.getForObject(url, Weather.class);
        } else {
            throw new RuntimeException("Location not found");
        }
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping(params = "name")
    public Optional<Location> getLocation(@RequestParam String name) {
        return repository.findByName(name);
    }

    @PostMapping
    public Location save(@RequestBody Location location) {
        return repository.save(location);
    }

    @PutMapping(params = "name")
    public Location updateLocation(@RequestParam String name, @RequestBody Location updatedLocation) {
        Optional<Location> locationOpt = repository.findByName(name);
        if (locationOpt.isPresent()) {
            Location location = locationOpt.get();
            location.setLatitude(updatedLocation.getLatitude());
            location.setLongitude(updatedLocation.getLongitude());
            location.setName(updatedLocation.getName());
            return repository.save(location);
        } else {
            throw new RuntimeException("Location not found");
        }
    }

    @DeleteMapping(params = "name")
    public void deleteLocation(@RequestParam String name) {
        Optional<Location> locationOpt = repository.findByName(name);
        if (locationOpt.isPresent()) {
            repository.delete(locationOpt.get());
        } else {
            throw new RuntimeException("Location not found");
        }
    }
}