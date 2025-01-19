package com.thy.challenge.dto;

import com.thy.challenge.entity.Transportation;

public class TransportationDTO {
    private Long id;
    private String origin;
    private String destination;
    private String type;

    public TransportationDTO(Transportation transportation) {
        this.id = transportation.getId();
        this.type = String.valueOf(transportation.getType());
        this.origin = transportation.getOrigin().getName();
        this.destination = transportation.getDestination().getName();
    }

    public TransportationDTO( String origin, String destination, String type) {
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

