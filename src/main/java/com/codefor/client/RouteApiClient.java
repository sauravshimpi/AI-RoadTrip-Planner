package com.codefor.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RouteApiClient {

    private final RestClient restClient;

    @Value("${openroute.api.key}")
    private String apiKey;

    @Value("${openroute.base-url}")
    private String baseUrl;

    @Value("${openroute.profile}")
    private String profile;

    public String getRoute(
            Double startLongitude,
            Double startLatitude,
            Double destinationLongitude,
            Double destinationLatitude) {

        Map<String, Object> body = Map.of(
                "coordinates",
                List.of(
                        List.of(startLongitude, startLatitude),
                        List.of(destinationLongitude, destinationLatitude)
                ),
                
                "radiuses",
                List.of(2000, 2000)
        );

        return restClient.post()
                .uri(baseUrl + "/v2/directions/" + profile + "/geojson")
                .header("Authorization", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(String.class);
    }
}