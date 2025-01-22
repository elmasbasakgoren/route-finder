package com.thy.challenge.request;

import com.thy.challenge.entity.TransportationType;

public class TransportationRequest {
    private TransportationType type;
    private Long originId;
    private Long destinationId;

    public TransportationType getType() {
        return type;
    }

    public void setType(TransportationType type) {
        this.type = type;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }
}

