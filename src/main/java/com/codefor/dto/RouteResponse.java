package com.codefor.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponse {

    private String startLocation;
    private String destination;
    private Double distance;
    private Integer durationMinutes;
    private String routeSummary;

    //we have added after the succesfully getting our output details on postman
    
//    private String geometry;
    
    private List<List<Double>> routeCoordinates;

}