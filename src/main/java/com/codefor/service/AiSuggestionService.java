package com.codefor.service;

import java.util.List;

import com.codefor.dto.AiSuggestionResponse;
import com.codefor.dto.PlaceResponse;
import com.codefor.dto.WeatherResponse;

public interface AiSuggestionService {

    AiSuggestionResponse generateSuggestions(
            String startLocation,
            String destination,
            Double distance,
            Integer durationMinutes,
            List<PlaceResponse> places,
            WeatherResponse weather
    );
}