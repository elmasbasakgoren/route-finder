package com.thy.challenge.service;

import com.thy.challenge.entity.Location;
import com.thy.challenge.entity.Transportation;
import com.thy.challenge.entity.TransportationType;
import com.thy.challenge.exceptions.ResourceNotFoundException;
import com.thy.challenge.repository.LocationRepository;
import com.thy.challenge.repository.TransportationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportationService {
    private final TransportationRepository transportationRepository;
    private final LocationRepository locationRepository;

    public TransportationService(TransportationRepository transportationRepository, LocationRepository locationRepository) {
        this.transportationRepository = transportationRepository;
        this.locationRepository = locationRepository;
    }

    public List<Transportation> findAll() {
        return transportationRepository.findAll();
    }

    public Transportation save(Transportation transportation) {
        return transportationRepository.save(transportation);
    }

    public void deleteById(Long id) {
        transportationRepository.deleteById(id);
    }

    public Transportation findById(Long id) {
        return transportationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transportation not found with id: " + id));
    }

    public List<Transportation> findByOriginAndDestination(Location origin, Location destination) {
        return transportationRepository.findByOriginAndDestination(origin, destination);
    }

    public List<List<Transportation>> findValidRoutes(Long originId, Long destinationId) {
        // Location nesnelerini repository'den alıyoruz
        Location origin = locationRepository.findById(originId)
                .orElseThrow(() -> new ResourceNotFoundException("Origin not found with id: " + originId));
        Location destination = locationRepository.findById(destinationId)
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + destinationId));

        List<List<Transportation>> validRoutes = new ArrayList<>();

        // 1. Uçuşları (FLIGHT) al
        List<Transportation> flights = transportationRepository.findAll()
                .stream()
                .filter(t -> t.getType() == TransportationType.FLIGHT)
                .collect(Collectors.toList());

        // 2. Her bir uçuşu kontrol et
        for (Transportation flight : flights) {
            if (flight.getOrigin().equals(origin)) {
                // Uçuş sonrası transferler
                List<Transportation> afterFlightTransfers = transportationRepository.findAll()
                        .stream()
                        .filter(t -> t.getOrigin().equals(flight.getDestination()) && t.getType() != TransportationType.FLIGHT)
                        .collect(Collectors.toList());

                if (flight.getDestination().equals(destination)) {
                    validRoutes.add(Collections.singletonList(flight));
                }

                for (Transportation after : afterFlightTransfers) {
                    if (after.getDestination().equals(destination)) {
                        validRoutes.add(Arrays.asList(flight, after));
                    }
                }
            } else if (flight.getDestination().equals(destination)) {
                // Uçuş öncesi transferler
                List<Transportation> beforeFlightTransfers = transportationRepository.findAll()
                        .stream()
                        .filter(t -> t.getDestination().equals(flight.getOrigin()) && t.getType() != TransportationType.FLIGHT)
                        .collect(Collectors.toList());

                for (Transportation before : beforeFlightTransfers) {
                    if (before.getOrigin().equals(origin)) {
                        validRoutes.add(Arrays.asList(before, flight));
                    }
                }
            } else {
                // Uçuş öncesi ve sonrası transferler
                List<Transportation> beforeFlightTransfers = transportationRepository.findAll()
                        .stream()
                        .filter(t -> t.getDestination().equals(flight.getOrigin()) && t.getType() != TransportationType.FLIGHT)
                        .collect(Collectors.toList());

                List<Transportation> afterFlightTransfers = transportationRepository.findAll()
                        .stream()
                        .filter(t -> t.getOrigin().equals(flight.getDestination()) && t.getType() != TransportationType.FLIGHT)
                        .collect(Collectors.toList());

                for (Transportation before : beforeFlightTransfers) {
                    if (before.getOrigin().equals(origin)) {
                        for (Transportation after : afterFlightTransfers) {
                            if (after.getDestination().equals(destination)) {
                                validRoutes.add(Arrays.asList(before, flight, after));
                            }
                        }
                    }
                }
            }
        }

        return validRoutes;
    }

}
