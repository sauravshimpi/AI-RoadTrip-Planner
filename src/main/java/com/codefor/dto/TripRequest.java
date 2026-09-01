package com.codefor.dto;

import jakarta.validation.constraints.NotBlank;


public class TripRequest {

    @NotBlank(message = "Start location is required")
    private String startLocation;

    @NotBlank(message = "Destination is required")
    private String destination;

    public TripRequest() {
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}