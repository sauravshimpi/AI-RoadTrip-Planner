package com.codefor.service;

import com.codefor.dto.RouteRequest;
import com.codefor.dto.TripPlanResponse;

public interface TripPlanService {

    TripPlanResponse generateTripPlan(RouteRequest request);
}