package com.thy.challenge.controller;

import com.thy.challenge.dto.TransportationDTO;
import com.thy.challenge.entity.Transportation;
import com.thy.challenge.repository.TransportationRepository;
import com.thy.challenge.service.TransportationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transportations")
public class TransportationController {
    private final TransportationService transportationService;
    private final TransportationRepository transportationRepository;

    public TransportationController(TransportationService transportationService, TransportationRepository transportationRepository) {
        this.transportationService = transportationService;
        this.transportationRepository = transportationRepository;
    }

    @GetMapping
    public List<TransportationDTO> getAllTransportations() {
        return transportationRepository.findAll()
                .stream()
                .map(transportation -> new TransportationDTO(transportation)) // Lambda kullanımı
                .collect(Collectors.toList());
    }


    @PostMapping
    public Transportation createTransportation(@RequestBody @Valid Transportation transportation) {
        return transportationService.save(transportation);
    }

    @GetMapping("/{id}")
    public Transportation getTransportationById(@PathVariable Long id) {
        return transportationService.findById(id);
    }

    @PutMapping("/{id}")
    public Transportation updateTransportation(@PathVariable Long id, @RequestBody @Valid Transportation transportationDetails) {
        Transportation transportation = transportationService.findById(id);
        transportation.setOrigin(transportationDetails.getOrigin());
        transportation.setDestination(transportationDetails.getDestination());
        transportation.setType(transportationDetails.getType());
        transportation.setOperatingDays(transportationDetails.getOperatingDays());
        return transportationService.save(transportation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransportation(@PathVariable Long id) {
        transportationService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/routes")
    public List<List<TransportationDTO>> getValidRoutes(@RequestParam Long originId, @RequestParam Long destinationId) {
        List<List<Transportation>> routes = transportationService.findValidRoutes(originId, destinationId);
        return routes.stream()
                .map(route -> route.stream()
                        .map(t -> new TransportationDTO(
                                t.getOrigin().getName(),
                                t.getDestination().getName(),
                                t.getType().toString()
                        ))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

}
