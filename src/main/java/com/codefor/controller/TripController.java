package com.codefor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codefor.dto.TripRequest;
import com.codefor.dto.TripResponse;
import com.codefor.service.TripService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    // CREATE TRIP
    @PostMapping
    public ResponseEntity<TripResponse> createTrip(
            @Valid @RequestBody TripRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        TripResponse response =
                tripService.createTrip(
                        request,
                        email
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // GET LOGGED-IN USER'S TRIPS
    @GetMapping
    public ResponseEntity<List<TripResponse>> getMyTrips(
            Authentication authentication) {

        String email = authentication.getName();

        List<TripResponse> trips =
                tripService.getMyTrips(email);

        return ResponseEntity.ok(trips);
    }
}