package com.thy.challenge.service;

import com.thy.challenge.entity.Location;
import com.thy.challenge.exceptions.ResourceNotFoundException;
import com.thy.challenge.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Location save(Location location) {
        boolean result = validate(location);
        if (result) {
            return locationRepository.save(location);
        } else {
            throw new IllegalArgumentException("locationCode is wrong");
        }
    }

    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    public Location findById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));
    }

    public boolean validate(Location location) {
        if (location.getLocationCode().length() > 3 && location.getName().toLowerCase(Locale.ROOT).contains("airport")) {
            return false;
        } else if (location.getLocationCode().length() > 30) {
            return false;
        }
        return true;
    }
}
