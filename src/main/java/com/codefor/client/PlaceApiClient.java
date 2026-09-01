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
public class PlaceApiClient {

    private final RestClient restClient;

    @Value("${openroute.api.key}")
    private String apiKey;

    @Value("${openroute.poi-url}")
    private String poiUrl;

    public String getPlaces(
            Double longitude,
            Double latitude) {

        Map<String, Object> geometry = Map.of(
                "geojson",
                Map.of(
                        "type", "Point",
                        "coordinates",
                        List.of(longitude, latitude)
                ),
                "buffer", 2000
        );

        Map<String, Object> filters = Map.of(
                "category_group_ids",
                List.of(220, 330, 620)
        );

        Map<String, Object> body = Map.of(
                "request", "pois",
                "geometry", geometry,
                "filters", filters,
                "limit", 20
        );

//        return restClient.post()
//                .uri(poiUrl)
//                .header("Authorization", apiKey)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(body)
//                .retrieve()
//                .body(String.class);
        
        return restClient.post()
                .uri(poiUrl)
                .header("Authorization", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        (request, response) -> {

                            String errorBody =
                                    new String(
                                            response.getBody().readAllBytes()
                                    );

                            System.out.println(
                                    "POI API ERROR: "
                                    + response.getStatusCode()
                            );

                            System.out.println(
                                    errorBody
                            );
                        }
                )
                .body(String.class);
    }
}