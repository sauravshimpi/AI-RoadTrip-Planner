package com.codefor.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripPlanResponse {

    private String startLocation;
    private String destination;

    private Double distance;
    private Integer durationMinutes;

    private List<List<Double>> routeCoordinates;

    private List<PlaceResponse> famousPlaces;

    private WeatherResponse weather;
}