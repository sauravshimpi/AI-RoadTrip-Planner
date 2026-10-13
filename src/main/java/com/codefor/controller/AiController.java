package com.codefor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codefor.client.GeminiApiClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final GeminiApiClient geminiApiClient;

    @GetMapping("/test")
    public ResponseEntity<String> testGemini() {

        String response =
                geminiApiClient.generateContent(
                        """
                        Suggest 3 useful tips for a road trip
                        from Pune to Mumbai.

                        Keep the answer short and practical.
                        """
                );

        return ResponseEntity.ok(response);
    }
}