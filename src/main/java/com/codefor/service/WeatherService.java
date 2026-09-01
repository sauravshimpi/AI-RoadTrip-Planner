package com.codefor.service;

import com.codefor.dto.WeatherResponse;

public interface WeatherService {

    WeatherResponse getWeather(
            Double latitude,
            Double longitude
    );
}