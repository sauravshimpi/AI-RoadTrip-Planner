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

        List<PlaceResponse> places = new ArrayList<>();

        try {

            String response =
                    placeApiClient.getPlaces(
                            longitude,
                            latitude
                    );
            
            System.out.println("ROUTE POI RESPONSE:");
            System.out.println(response);

            if (response == null || response.isBlank()) {
                return places;
            }

            response = response.replace("NaN", "null");

            JsonNode root =
                    objectMapper.readTree(response);

            JsonNode features =
                    root.path("features");

            if (!features.isArray()) {
                return places;
            }

            for (JsonNode feature : features) {

                String name = feature
                        .path("properties")
                        .path("osm_tags")
                        .path("name")
                        .asText("Unknown Place");

                if ("Unknown Place".equalsIgnoreCase(name)) {
                    continue;
                }

                JsonNode coordinates = feature
                        .path("geometry")
                        .path("coordinates");

                if (!coordinates.isArray()
                        || coordinates.size() < 2
                        || coordinates.get(0).isNull()
                        || coordinates.get(1).isNull()) {

                    continue;
                }

                Double placeLongitude =
                        coordinates.get(0).asDouble();

                Double placeLatitude =
                        coordinates.get(1).asDouble();

                PlaceResponse place =
                        new PlaceResponse(
                                name,
                                "Tourist Place",
                                placeLongitude,
                                placeLatitude
                        );

                places.add(place);
            }

            return places;

        } catch (Exception e) {

            System.out.println(
                    "Places API Error for longitude: "
                            + longitude
                            + ", latitude: "
                            + latitude
            );

            e.printStackTrace();

            return places;
        }
    }

    @Override
    public List<PlaceResponse> getPlacesAlongRoute(
            List<List<Double>> routeCoordinates) {

        List<PlaceResponse> places = new ArrayList<>();

        if (routeCoordinates == null
                || routeCoordinates.size() < 2) {

            return places;
        }

        try {

            String response =
                    placeApiClient
                            .getPlacesAlongRoute(
                                    routeCoordinates
                            );

            if (response == null || response.isBlank()) {
                return places;
            }

            response = response.replace("NaN", "null");

            JsonNode root =
                    objectMapper.readTree(response);

            JsonNode features =
                    root.path("features");

            if (!features.isArray()) {
                return places;
            }

            for (JsonNode feature : features) {

                String name = feature
                        .path("properties")
                        .path("osm_tags")
                        .path("name")
                        .asText("Unknown Place");

                if ("Unknown Place".equalsIgnoreCase(name)) {
                    continue;
                }

                JsonNode coordinates = feature
                        .path("geometry")
                        .path("coordinates");

                if (!coordinates.isArray()
                        || coordinates.size() < 2
                        || coordinates.get(0).isNull()
                        || coordinates.get(1).isNull()) {

                    continue;
                }

                Double longitude =
                        coordinates.get(0).asDouble();

                Double latitude =
                        coordinates.get(1).asDouble();

                boolean alreadyExists =
                        places.stream()
                                .anyMatch(existing ->
                                        existing.getName() != null
                                                &&
                                        existing.getName()
                                                .equalsIgnoreCase(name)
                                );

                if (!alreadyExists) {

                    PlaceResponse place =
                            new PlaceResponse(
                                    name,
                                    "Tourist Place",
                                    longitude,
                                    latitude
                            );

                    places.add(place);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to find tourist places along route"
            );

            e.printStackTrace();
        }

        return places;
    }
}