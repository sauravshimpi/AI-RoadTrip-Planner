package com.codefor.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codefor.client.GeminiApiClient;
import com.codefor.dto.AiSuggestionResponse;
import com.codefor.dto.PlaceResponse;
import com.codefor.dto.WeatherResponse;
import com.codefor.service.AiSuggestionService;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class AiSuggestionServiceImpl implements AiSuggestionService {

    private final GeminiApiClient geminiApiClient;
    private final ObjectMapper objectMapper;

    @Override
    public AiSuggestionResponse generateSuggestions(
            String startLocation,
            String destination,
            Double distance,
            Integer durationMinutes,
            List<PlaceResponse> places,
            WeatherResponse weather) {

        String prompt = buildPrompt(
                startLocation,
                destination,
                distance,
                durationMinutes,
                places,
                weather
        );

        String aiResponse =
                geminiApiClient.generateContent(prompt);

        return convertToResponse(aiResponse);
    }

    private AiSuggestionResponse convertToResponse(
            String aiResponse) {

        try {

            String cleanedResponse =
                    aiResponse
                            .replace("```json", "")
                            .replace("```", "")
                            .trim();

            return objectMapper.readValue(
                    cleanedResponse,
                    AiSuggestionResponse.class
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to convert Gemini response into AI suggestions",
                    e
            );
        }
    }

    private String buildPrompt(
            String startLocation,
            String destination,
            Double distance,
            Integer durationMinutes,
            List<PlaceResponse> places,
            WeatherResponse weather) {

        StringBuilder prompt =
                new StringBuilder();

        prompt.append("""
                You are an AI road trip assistant.

                Use ONLY the factual trip information given below.

                Trip Information:
                """);

        prompt.append("Start Location: ")
                .append(startLocation)
                .append("\n");

        prompt.append("Destination: ")
                .append(destination)
                .append("\n");

        prompt.append("Distance: ")
                .append(distance)
                .append(" km\n");

        prompt.append("Travel Duration: ")
                .append(durationMinutes)
                .append(" minutes\n\n");

        prompt.append("Famous Places Along Route:\n");

        if (places != null && !places.isEmpty()) {

            for (PlaceResponse place : places) {

                prompt.append("- ")
                        .append(place.getName())
                        .append("\n");
            }

        } else {

            prompt.append("- No famous places available\n");
        }

        prompt.append("\nDestination Weather:\n");

        if (weather != null) {

            prompt.append("Temperature: ")
                    .append(weather.getTemperature())
                    .append(" C\n");

            prompt.append("Feels Like: ")
                    .append(weather.getFeelsLike())
                    .append(" C\n");

            prompt.append("Humidity: ")
                    .append(weather.getHumidity())
                    .append("%\n");

            prompt.append("Precipitation: ")
                    .append(weather.getPrecipitation())
                    .append("\n");

            prompt.append("Condition: ")
                    .append(weather.getCondition())
                    .append("\n");
        }

        prompt.append("""
                
                Return ONLY valid JSON.

                Do not return markdown.
                Do not use ```json.
                Do not write anything before or after the JSON.

                Use exactly this structure:

                {
                  "tripSummary": "short summary",
                  "recommendedStops": [
                    "stop 1",
                    "stop 2"
                  ],
                  "travelTips": [
                    "tip 1",
                    "tip 2"
                  ],
                  "weatherAdvice": "weather advice",
                  "staySuggestions": [
                    "stay suggestion 1",
                    "stay suggestion 2"
                  ]
                }

                IMPORTANT RULES:

                - Do not change the supplied distance.
                - Do not change the supplied travel duration.
                - Do not invent weather information.
                - Do not calculate another route.
                - Use the famous places provided above when suggesting stops.
                - Keep recommendations practical.
                - Keep the response concise.
                """);

        return prompt.toString();
    }
}