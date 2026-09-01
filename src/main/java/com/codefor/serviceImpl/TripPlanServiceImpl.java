package com.codefor.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codefor.dto.PlaceResponse;
import com.codefor.dto.RouteRequest;
import com.codefor.dto.RouteResponse;
import com.codefor.dto.TripPlanResponse;
import com.codefor.dto.WeatherResponse;
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

    @Override
    public TripPlanResponse generateTripPlan(RouteRequest request) {

        // 1. Generate route
        RouteResponse route =
                routeService.generateRoute(request);

        // 2. Get famous places along route
        List<PlaceResponse> places =
                placeService.getPlacesAlongRoute(
                        route.getRouteCoordinates()
                );

        // 3. Get destination coordinates
        List<List<Double>> coordinates =
                route.getRouteCoordinates();

        List<Double> destinationPoint =
                coordinates.get(coordinates.size() - 1);

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

        // 5. Create complete response
        return new TripPlanResponse(
                route.getStartLocation(),
                route.getDestination(),
                route.getDistance(),
                route.getDurationMinutes(),
                route.getRouteCoordinates(),
                places,
                weather
        );
    }
}