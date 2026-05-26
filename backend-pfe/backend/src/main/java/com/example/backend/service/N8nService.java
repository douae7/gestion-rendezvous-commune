package com.example.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class N8nService {

    @Value("${n8n.webhook.url}")
    private String webhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String sendMessage(String userId, String message) {
        try {
            Map<String, String> body = new HashMap<>();
            body.put("message", message);
            body.put("userId", userId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    webhookUrl, request, Map.class
            );

            String reply = (String) response.getBody().get("response");
            return reply != null ? reply : "Pas de réponse.";

        } catch (Exception e) {
            return "Erreur de connexion à l'assistant.";
        }
    }
}