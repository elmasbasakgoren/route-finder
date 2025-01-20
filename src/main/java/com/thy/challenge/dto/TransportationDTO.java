package com.thy.challenge.dto;

import com.thy.challenge.entity.Transportation;

public class TransportationDTO {
    private Long id;
    private String origin;
    private Long originId;
    private String destination;
    private Long destinationId;
    private String type;

    public TransportationDTO(Transportation transportation) {
        this.id = transportation.getId();
        this.type = String.valueOf(transportation.getType());
        this.origin = transportation.getOrigin().getName();
        this.originId = transportation.getOrigin().getId(); // Origin ID
        this.destination = transportation.getDestination().getName();
        this.destinationId = transportation.getDestination().getId(); // Destination ID
    }

    public TransportationDTO(String origin, String destination, String type) {
        this.origin = origin;
        this.destination = destination;
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
