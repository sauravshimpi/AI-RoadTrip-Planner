package com.codefor.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.codefor.client.GeocodingClient;
import com.codefor.client.RouteApiClient;
import com.codefor.dto.CordinateResponse;
import com.codefor.dto.RouteRequest;
import com.codefor.dto.RouteResponse;
import com.codefor.service.RouteService;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final GeocodingClient geocodingClient;
    private final RouteApiClient routeApiClient;
    private final ObjectMapper objectMapper;

    @Override
    public RouteResponse generateRoute(RouteRequest request) {

        // Get starting-location coordinates
        CordinateResponse start =
                geocodingClient.getCoordinates(
                        request.getStartLocation()
                );

        // Get destination coordinates
        CordinateResponse destination =
                geocodingClient.getCoordinates(
                        request.getDestination()
                );

        // Get route from OpenRouteService
        String response =
                routeApiClient.getRoute(
                        start.getLongitude(),
                        start.getLatitude(),
                        destination.getLongitude(),
                        destination.getLatitude()
                );

        try {

            // Convert JSON response into JsonNode
            JsonNode root = objectMapper.readTree(response);

            // GeoJSON response contains the "features" array
            JsonNode features = root.path("features");

            if (!features.isArray() || features.isEmpty()) {
                throw new RuntimeException(
                        "No route found. OpenRouteService response: "
                                + response
                );
            }

            // Get the first route feature
            JsonNode feature = features.get(0);

            // Get route summary
            JsonNode summary =
                    feature.path("properties")
                           .path("summary");

            if (summary.isMissingNode() || summary.isNull()) {
                throw new RuntimeException(
                        "Route summary not found. OpenRouteService response: "
                                + response
                );
            }

            // Get distance in meters
            double distanceInMeters =
                    summary.path("distance").asDouble();

            // Get duration in seconds
            double durationInSeconds =
                    summary.path("duration").asDouble();

            // Convert meters into kilometers
            double distanceInKm =
                    Math.round((distanceInMeters / 1000.0) * 100.0)
                            / 100.0;

            // Convert seconds into minutes
            int durationInMinutes =
                    (int) Math.round(durationInSeconds / 60.0);

            // Get route geometry from the first feature
            String geometry =
                    objectMapper.writeValueAsString(
                            feature.path("geometry")
                    );

            //FOR GETTING COORDINATES for connecting react
            
            JsonNode coordinates =
                    feature.path("geometry")
                           .path("coordinates");

            List<List<Double>> routeCoordinates = new ArrayList<>();

            int step = 5;

            for (int i = 0; i < coordinates.size(); i += step) {

                JsonNode point = coordinates.get(i);

                routeCoordinates.add(
                        List.of(
                                point.get(0).asDouble(),
                                point.get(1).asDouble()
                        )
                );
            }

            // Always keep destination coordinate
            if (!coordinates.isEmpty()) {

                JsonNode lastPoint =
                        coordinates.get(coordinates.size() - 1);

                List<Double> destinationCoordinate =
                        List.of(
                                lastPoint.get(0).asDouble(),
                                lastPoint.get(1).asDouble()
                        );

                if (!routeCoordinates
                        .get(routeCoordinates.size() - 1)
                        .equals(destinationCoordinate)) {

                    routeCoordinates.add(destinationCoordinate);
                }
            }
            return new RouteResponse(
                    request.getStartLocation(),
                    request.getDestination(),
                    distanceInKm,
                    durationInMinutes,
                    "Route calculated successfully",
                    routeCoordinates
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to process route response: "
                            + e.getMessage(),
                    e
            );
        }
    }
}