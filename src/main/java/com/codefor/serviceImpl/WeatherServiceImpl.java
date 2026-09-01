package com.codefor.serviceImpl;

import org.springframework.stereotype.Service;

import com.codefor.client.WeatherApiClient;
import com.codefor.dto.WeatherResponse;
import com.codefor.service.WeatherService;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl
        implements WeatherService {

    private final WeatherApiClient weatherApiClient;

    private final ObjectMapper objectMapper;

    @Override
    public WeatherResponse getWeather(
            Double latitude,
            Double longitude) {

        String response =
                weatherApiClient.getWeather(
                        latitude,
                        longitude
                );

        try {

            JsonNode root =
                    objectMapper.readTree(response);

            JsonNode current =
                    root.path("current");

            Double temperature =
                    current.path("temperature_2m")
                           .asDouble();

            Double feelsLike =
                    current.path("apparent_temperature")
                           .asDouble();

            Integer humidity =
                    current.path("relative_humidity_2m")
                           .asInt();

            Double precipitation =
                    current.path("precipitation")
                           .asDouble();

            int weatherCode =
                    current.path("weather_code")
                           .asInt();

            String condition =
                    getWeatherCondition(weatherCode);

            return new WeatherResponse(
                    temperature,
                    feelsLike,
                    humidity,
                    precipitation,
                    condition
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to process weather response",
                    e
            );
        }
    }


    private String getWeatherCondition(
            int code) {

        if (code == 0) {
            return "Clear";
        }

        if (code >= 1 && code <= 3) {
            return "Cloudy";
        }

        if (code >= 45 && code <= 48) {
            return "Fog";
        }

        if (code >= 51 && code <= 67) {
            return "Rain";
        }

        if (code >= 71 && code <= 77) {
            return "Snow";
        }

        if (code >= 80 && code <= 82) {
            return "Rain Showers";
        }

        if (code >= 95) {
            return "Thunderstorm";
        }

        return "Unknown";
    }
}