package com.codefor.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

//import com.codefor.dto.CoordinateResponse;
import com.codefor.dto.CordinateResponse;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class GeocodingClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${openroute.api.key}")
    private String apiKey;

    @Value("${openroute.base-url}")
    private String baseUrl;

    public CordinateResponse getCoordinates(String location) {

        String response = restClient.get()
                .uri(baseUrl + "/geocode/search"
                        + "?api_key=" + apiKey
                        + "&text=" + location)
                .retrieve()
                .body(String.class);

        try {

            JsonNode root = objectMapper.readTree(response);

            JsonNode coordinates =
                    root.path("features")
                        .get(0)
                        .path("geometry")
                        .path("coordinates");

            Double longitude = coordinates.get(0).asDouble();
            Double latitude = coordinates.get(1).asDouble();

            return new CordinateResponse(
                    longitude,
                    latitude
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to find coordinates for: " + location,
                    e
            );
        }
    }
}