package com.example.location.controller;

import com.example.location.model.Location;
import com.example.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public Iterable<Location> findAll() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> findById(@PathVariable int id) {
        Optional<Location> location = locationService.findById(id);
        return location.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Location> save(@RequestBody Location location) {
        if (locationService.existsById(location.getId())) {
            return new ResponseEntity<>(locationService.findById(location.getId()).get(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(locationService.save(location), HttpStatus.CREATED);
        }
    }
}