package com.thy.challenge.controller;

import com.thy.challenge.entity.Location;
import com.thy.challenge.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.findAll();
    }

    @PostMapping
    public Location createLocation(@RequestBody @Valid Location location) {
        return locationService.save(location);
    }

    @GetMapping("/{id}")
    public Location getLocationById(@PathVariable Long id) {
        return locationService.findById(id);
    }

    @PutMapping("/{id}")
    public Location updateLocation(@PathVariable Long id, @RequestBody @Valid Location locationDetails) {
        Location location = locationService.findById(id);
        location.setName(locationDetails.getName());
        location.setCountry(locationDetails.getCountry());
        location.setCity(locationDetails.getCity());
        location.setLocationCode(locationDetails.getLocationCode());
        return locationService.save(location);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        locationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
