package com.codefor.service;

import java.util.List;

import com.codefor.dto.TripRequest;
import com.codefor.dto.TripResponse;

public interface TripService {

    TripResponse createTrip(TripRequest request, String email);

    List<TripResponse> getMyTrips(String email);
}