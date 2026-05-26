package com.example.backend.controller;

import com.example.backend.service.N8nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    @Autowired
    private N8nService n8nService;

    @PostMapping
    public ResponseEntity<String> chat(@RequestParam String userId,
                                       @RequestBody String message) {
        return ResponseEntity.ok(n8nService.sendMessage(userId, message));
    }
}