package com.codefor.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codefor.client.GeocodingClient;
import com.codefor.dto.LocationSuggestionResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LocationController {

    private final GeocodingClient geocodingClient;

    @GetMapping("/search")
    public List<LocationSuggestionResponse> search(
            @RequestParam String query) {

        return geocodingClient.searchLocations(query);
    }
}