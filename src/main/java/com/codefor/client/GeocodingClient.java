package com.codefor.client;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.codefor.dto.CordinateResponse;
import com.codefor.dto.LocationSuggestionResponse;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class GeocodingClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${openroute.api.key}")
    private String apiKey;

    public CordinateResponse getCoordinates(String location) {

        try {

            String response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("api.heigit.org")
                            .path("/pelias/v1/search")
                            .queryParam("api_key", apiKey)
                            .queryParam("text", location)
                            .queryParam("boundary.country", "IN")
                            .queryParam("size", 5)
                            .build()
                    )
                    .retrieve()
                    .body(String.class);

            JsonNode root =
                    objectMapper.readTree(response);

            JsonNode features =
                    root.path("features");

            if (!features.isArray() || features.isEmpty()) {

                throw new RuntimeException(
                        "Location not found: " + location
                );
            }

            JsonNode coordinates =
                    features.get(0)
                            .path("geometry")
                            .path("coordinates");

            if (!coordinates.isArray()
                    || coordinates.size() < 2) {

                throw new RuntimeException(
                        "Coordinates not found for: " + location
                );
            }

            Double longitude =
                    coordinates.get(0).asDouble();

            Double latitude =
                    coordinates.get(1).asDouble();

            System.out.println(
                    "Location: " + location
            );

            System.out.println(
                    "Latitude: " + latitude
                            + ", Longitude: " + longitude
            );

            return new CordinateResponse(
                    latitude,
                    longitude
            );

        } catch (Exception e) {

            System.out.println(
                    "GEOCODING ERROR FOR: " + location
            );

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to get coordinates for: "
                            + location,
                    e
            );
        }
    }
    
    
    public List<LocationSuggestionResponse> searchLocations(String location) {

        try {

            String encodedLocation =
                    URLEncoder.encode(
                            location,
                            StandardCharsets.UTF_8
                    );

            String url =
                    "https://api.heigit.org/pelias/v1/search"
                    + "?api_key=" + apiKey
                    + "&text=" + encodedLocation
                    + "&size=5"
                    + "&boundary.country=IN";

            String response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(String.class);

            JsonNode root =
                    objectMapper.readTree(response);

            JsonNode features =
                    root.path("features");

            List<LocationSuggestionResponse> suggestions =
                    new ArrayList<>();

            for (JsonNode feature : features) {

                String label = feature
                        .path("properties")
                        .path("label")
                        .asText("");

                JsonNode coordinates = feature
                        .path("geometry")
                        .path("coordinates");

                if (label.isBlank()
                        || !coordinates.isArray()
                        || coordinates.size() < 2) {

                    continue;
                }

                Double longitude =
                        coordinates.get(0).asDouble();

                Double latitude =
                        coordinates.get(1).asDouble();

                suggestions.add(
                        new LocationSuggestionResponse(
                                label,
                                latitude,
                                longitude
                        )
                );
            }

            return suggestions;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to search location: " + location,
                    e
            );
        }
    }
}