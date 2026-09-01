package com.codefor.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.codefor.client.PlaceApiClient;
import com.codefor.dto.PlaceResponse;
import com.codefor.service.PlaceService;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceApiClient placeApiClient;
    private final ObjectMapper objectMapper;

    @Override
    public List<PlaceResponse> getNearbyPlaces(
            Double longitude,
            Double latitude) {

        String response =
                placeApiClient.getPlaces(longitude, latitude);

        List<PlaceResponse> places = new ArrayList<>();

        try {

            JsonNode root = objectMapper.readTree(response);

            JsonNode features = root.path("features");

            for (JsonNode feature : features) {

                String name = feature
                        .path("properties")
                        .path("osm_tags")
                        .path("name")
                        .asText("Unknown Place");

                JsonNode coordinates = feature
                        .path("geometry")
                        .path("coordinates");

                Double placeLongitude =
                        coordinates.get(0).asDouble();

                Double placeLatitude =
                        coordinates.get(1).asDouble();

                String category = "Tourist Place";

                PlaceResponse place = new PlaceResponse(
                        name,
                        category,
                        placeLongitude,
                        placeLatitude
                );

                places.add(place);
            }

            return places;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to process places response",
                    e
            );
        }
    }

    @Override
    public List<PlaceResponse> getPlacesAlongRoute(
            List<List<Double>> routeCoordinates) {

        List<PlaceResponse> allPlaces = new ArrayList<>();

        if (routeCoordinates == null || routeCoordinates.isEmpty()) {
            return allPlaces;
        }

        int totalPoints = routeCoordinates.size();
        int step = Math.max(totalPoints / 5, 1);

        for (int i = 0; i < totalPoints; i += step) {

            List<Double> point = routeCoordinates.get(i);

            Double longitude = point.get(0);
            Double latitude = point.get(1);

            List<PlaceResponse> nearbyPlaces =
                    getNearbyPlaces(longitude, latitude);

            for (PlaceResponse place : nearbyPlaces) {

                boolean alreadyExists = allPlaces.stream()
                        .anyMatch(existing ->
                                existing.getName()
                                        .equalsIgnoreCase(place.getName())
                        );

                if (!alreadyExists
                        && !"Unknown Place".equalsIgnoreCase(place.getName())) {

                    allPlaces.add(place);
                }
            }
        }

        return allPlaces;
    }
}