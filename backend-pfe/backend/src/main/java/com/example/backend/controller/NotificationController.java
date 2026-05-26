package com.example.backend.controller;

import com.example.backend.entity.Notification;
import com.example.backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return service.create(notification);
    }

    @GetMapping
    public List<Notification> getNotifications(
            @RequestParam Long userId,
            @RequestParam String role
    ) {
        return service.getUserNotifications(userId, role);
    }

    // ✅ CORRIGÉ - Retourne la notification mise à jour
    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable Long id) {
        return service.markAsRead(id);
    }

    // ✅ CORRIGÉ - Marquer TOUT comme lu
    @PutMapping("/read-all")
    public ResponseEntity<?> markAllAsRead(
            @RequestParam Long userId,
            @RequestParam String role
    ) {
        service.markAllAsRead(userId, role);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}