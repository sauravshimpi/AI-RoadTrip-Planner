package com.codefor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codefor.dto.RouteRequest;
import com.codefor.dto.TripPlanResponse;
import com.codefor.service.TripPlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trip-plan")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TripPlanController {

    private final TripPlanService tripPlanService;

    @PostMapping
    public ResponseEntity<TripPlanResponse> generateTrip(
            @Valid @RequestBody RouteRequest request) {

        TripPlanResponse response = tripPlanService.generateTripPlan(request);

        return ResponseEntity.ok(response);
    }
}