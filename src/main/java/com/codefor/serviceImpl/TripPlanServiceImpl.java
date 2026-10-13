package com.codefor.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codefor.dto.AiSuggestionResponse;
import com.codefor.dto.PlaceResponse;
import com.codefor.dto.RouteRequest;
import com.codefor.dto.RouteResponse;
import com.codefor.dto.TripPlanResponse;
import com.codefor.dto.WeatherResponse;
import com.codefor.service.AiSuggestionService;
import com.codefor.service.PlaceService;
import com.codefor.service.RouteService;
import com.codefor.service.TripPlanService;
import com.codefor.service.WeatherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripPlanServiceImpl implements TripPlanService {

    private final RouteService routeService;

    private final PlaceService placeService;

    private final WeatherService weatherService;

    private final AiSuggestionService aiSuggestionService;

    @Override
    public TripPlanResponse generateTripPlan(
            RouteRequest request) {

        // 1. Generate route
        RouteResponse route =
                routeService.generateRoute(request);

        // 2. Find famous places along route
        List<PlaceResponse> places =
                placeService.getPlacesAlongRoute(
                        route.getRouteCoordinates()
                );

        // 3. Get destination coordinate
        List<List<Double>> coordinates =
                route.getRouteCoordinates();

        List<Double> destinationPoint =
                coordinates.get(
                        coordinates.size() - 1
                );

        // Coordinate format:
        // [longitude, latitude]

        Double destinationLongitude =
                destinationPoint.get(0);

        Double destinationLatitude =
                destinationPoint.get(1);

        // 4. Get destination weather
        WeatherResponse weather =
                weatherService.getWeather(
                        destinationLatitude,
                        destinationLongitude
                );

        // 5. Generate AI suggestions
        AiSuggestionResponse aiSuggestions =
                aiSuggestionService.generateSuggestions(
                        route.getStartLocation(),
                        route.getDestination(),
                        route.getDistance(),
                        route.getDurationMinutes(),
                        places,
                        weather
                );

        // 6. Convert minutes into readable duration
        String formattedDuration =
                formatDuration(
                        route.getDurationMinutes()
                );

        // 7. Create final response
        return new TripPlanResponse(
                route.getStartLocation(),
                route.getDestination(),
                route.getDistance(),
                route.getDurationMinutes(),
                formattedDuration,
                route.getRouteCoordinates(),
                places,
                weather,
                aiSuggestions
        );
    }

    private String formatDuration(
            Integer durationMinutes) {

        if (durationMinutes == null) {
            return "N/A";
        }

        int hours =
                durationMinutes / 60;

        int minutes =
                durationMinutes % 60;

        if (hours == 0) {

            return minutes + " min";
        }

        if (minutes == 0) {

            return hours + " hr";
        }

        return hours
                + " hr "
                + minutes
                + " min";
    }
}