package com.example.backend.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // FRONTEND → /app/send
    @MessageMapping("/send")
    public void sendNotification(String message) {

        System.out.println("📩 Message reçu via WebSocket: " + message);

        // BACKEND → FRONTEND
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}