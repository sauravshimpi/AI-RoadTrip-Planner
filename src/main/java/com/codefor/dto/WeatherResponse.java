package com.codefor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {

    private Double temperature;

    private Double feelsLike;

    private Integer humidity;

    private Double precipitation;

    private String condition;
}