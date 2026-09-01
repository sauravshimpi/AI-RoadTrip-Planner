package com.codefor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripResponse {

    private Long id;
    private String startLocation;
    private String destination;
    private Double distance;

    private String duration;

    private String routeSummary;
//    private String geometry;
}