package com.codefor.controller;

import org.springframework.http.ResponseEntity;
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
public class TripPlanController {

    private final TripPlanService tripPlanService;

    @PostMapping
    public ResponseEntity<TripPlanResponse> generateTripPlan(
            @Valid @RequestBody RouteRequest request) {

        return ResponseEntity.ok(
                tripPlanService.generateTripPlan(request)
        );
    }
}