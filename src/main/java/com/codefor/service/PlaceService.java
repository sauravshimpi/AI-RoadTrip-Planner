package com.codefor.service;

import java.util.List;

import com.codefor.dto.PlaceResponse;

public interface PlaceService {

    List<PlaceResponse> getNearbyPlaces(
            Double longitude,
            Double latitude
    );
    
    List<PlaceResponse> getPlacesAlongRoute(
            List<List<Double>> routeCoordinates
    );
}