package com.codefor.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiSuggestionResponse {

    private String tripSummary;

    private List<String> recommendedStops;

    private List<String> travelTips;

    private String weatherAdvice;

    private List<String> staySuggestions;
}