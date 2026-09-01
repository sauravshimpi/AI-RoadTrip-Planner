package com.codefor.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeatherApiClient {

    private final RestClient restClient;

    public String getWeather(
            Double latitude,
            Double longitude) {

        String url =
                "https://api.open-meteo.com/v1/forecast"
                + "?latitude=" + latitude
                + "&longitude=" + longitude
                + "&current=temperature_2m,"
                + "apparent_temperature,"
                + "relative_humidity_2m,"
                + "precipitation,"
                + "weather_code";

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
    }
}