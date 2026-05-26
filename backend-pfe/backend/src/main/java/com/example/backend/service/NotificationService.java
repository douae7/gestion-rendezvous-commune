package com.example.backend.service;

import com.example.backend.entity.Notification;
import com.example.backend.entity.User;
import com.example.backend.enums.NotificationType;
import com.example.backend.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    public Notification create(Notification notification) {
        System.out.println("🆕 Création notif: " + notification.getTitle());
        Notification saved = repository.save(notification);
        sendRealtime(saved);
        return saved;
    }


    public List<Notification> getUserNotifications(Long userId, String role) {
        return repository.findByUserIdOrRoleOrderByCreatedAtDesc(userId, role);
    }

    public Notification markAsRead(Long id) {
        Notification n = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));
        n.setRead(true);  // ✅ setIsRead (pas setRead)
        Notification saved = repository.save(n);
        sendRealtime(saved);
        return saved;
    }

    @Transactional
    public void markAllAsRead(Long userId, String role) {

        if ("citizen".equalsIgnoreCase(role)) {
            repository.markAllAsReadByUser(userId);
        } else {
            repository.markAllAsReadByRole(role);
        }
    }

    public void delete(Long id) {
        System.out.println("🗑️ DELETE notif ID: " + id);

        // ✅ SAFE : Vérifie d'abord si existe
        if (!repository.existsById(id)) {
            System.out.println("⚠️ Notif ID " + id + " n'existe pas");
            return; // ← Pas de crash !
        }

        Notification n = repository.findById(id).get(); // Safe maintenant

        Long userId = n.getUser() != null ? n.getUser().getId() : null;
        String role = n.getRole();

        repository.deleteById(id);

        // Broadcast mise à jour
        if (userId != null) {
            List<Notification> updated = getUserNotifications(userId, role);
            messagingTemplate.convertAndSend("/topic/notifications/user/" + userId, updated);
        }
        if (role != null) {
            List<Notification> updated = getUserNotifications(0L, role);
            messagingTemplate.convertAndSend("/topic/notifications/role/" + role, updated);
        }

        System.out.println("✅ Notif " + id + " supprimée");
    }

    // ✅ notifyUser (existant)
    public void notifyUser(User user, String title, String message, NotificationType type) {
        if (user == null) return;

        Notification notification = Notification.builder()
                .title(title)
                .message(message)
                .type(type)
                .isRead(false)
                .user(user)
                .role(null)
                .build();
        create(notification);
    }

    // ✅ notifyRole (CORRIGÉ)
    public void notifyRole(String role, String title, String message, NotificationType type) {
        if (role == null || role.trim().isEmpty()) {
            System.out.println("⚠️ notifyRole: role vide");
            return;
        }

        System.out.println("🔥 notifyRole → " + role.toUpperCase() + ": " + title);

        Notification notification = Notification.builder()
                .title(title)
                .message(message)
                .type(type)
                .isRead(false)
                .user(null)
                .role(role.toUpperCase())  // "ADMIN", "AGENT", "CITOYEN"
                .build();

        create(notification);
    }

    private void sendRealtime(Notification notification) {
        System.out.println("📡 WebSocket → user:" +
                (notification.getUser() != null ? notification.getUser().getId() : "null") +
                " role:" + notification.getRole());

        // ✅ NULL SAFE
        if (notification.getUser() != null) {
            Long userId = notification.getUser().getId();
            if (userId != null) {
                messagingTemplate.convertAndSend(
                        "/topic/notifications/user/" + userId,
                        notification
                );
                System.out.println("✅ Sent USER: /topic/notifications/user/" + userId);
            }
        }

        if (notification.getRole() != null && !notification.getRole().trim().isEmpty()) {
            messagingTemplate.convertAndSend(
                    "/topic/notifications/role/" + notification.getRole(),
                    notification
            );
            System.out.println("✅ Sent ROLE: /topic/notifications/role/" + notification.getRole());
        }
    }

}