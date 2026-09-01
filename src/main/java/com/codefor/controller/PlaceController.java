package com.codefor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codefor.dto.PlaceResponse;
import com.codefor.service.PlaceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceResponse>> getPlaces(
            @RequestParam Double longitude,
            @RequestParam Double latitude) {

        return ResponseEntity.ok(
                placeService.getNearbyPlaces(
                        longitude,
                        latitude
                )
        );
    }
}