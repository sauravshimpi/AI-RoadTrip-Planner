package com.codefor.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codefor.dto.RouteRequest;
import com.codefor.dto.RouteResponse;
import com.codefor.dto.TripRequest;
import com.codefor.dto.TripResponse;
import com.codefor.entity.Trip;
import com.codefor.entity.User;
import com.codefor.repository.TripRepository;
import com.codefor.repository.UserRepository;
import com.codefor.service.RouteService;
import com.codefor.service.TripService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    
    private final RouteService routeService;
    

    @Override
    public TripResponse createTrip(
            TripRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        RouteRequest routeRequest = new RouteRequest();

        routeRequest.setStartLocation(
                request.getStartLocation()
        );

        routeRequest.setDestination(
                request.getDestination()
        );

        RouteResponse routeResponse =
                routeService.generateRoute(routeRequest);

        Trip trip = new Trip();

        trip.setStartLocation(
                request.getStartLocation()
        );

        trip.setDestination(
                request.getDestination()
        );

        trip.setDistance(
                routeResponse.getDistance()
        );

        trip.setDurationMinutes(
                routeResponse.getDurationMinutes()
        );

        trip.setRouteSummary(
                routeResponse.getRouteSummary()
        );
        
        //after the trip successfully added 
//        trip.setGeometry(
//                routeResponse.getGeometry()
//        );
//        
        

        trip.setCreatedAt(
                LocalDateTime.now()
        );

        trip.setUser(user);

        Trip savedTrip =
                tripRepository.save(trip);

        return new TripResponse(
                savedTrip.getId(),
                savedTrip.getStartLocation(),
                savedTrip.getDestination(),
                savedTrip.getDistance(),
                formatDuration(savedTrip.getDurationMinutes()),
                savedTrip.getRouteSummary()
                
               // savedTrip.getGeometry()
        );
    }

    @Override
    public List<TripResponse> getMyTrips(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        List<Trip> trips = tripRepository.findByUser(user);

        return trips.stream()
                .map(trip -> new TripResponse(
                        trip.getId(),
                        trip.getStartLocation(),
                        trip.getDestination(),
                        trip.getDistance(),
//                        trip.getDurationMinutes(),
                        formatDuration(trip.getDurationMinutes()),
                        trip.getRouteSummary()
                       
                ))
                .toList();
    }
    
    
    private String formatDuration(Integer minutes) {

        if (minutes == null) {
            return null;
        }

        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;

        return hours + " hr (" + remainingMinutes + " min)";
    }
}