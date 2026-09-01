package com.codefor.service;

import com.codefor.dto.RouteRequest;
import com.codefor.dto.RouteResponse;

public interface RouteService {

    RouteResponse generateRoute(RouteRequest request);
}