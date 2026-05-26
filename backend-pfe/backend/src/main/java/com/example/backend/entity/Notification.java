package com.example.backend.entity;

import com.example.backend.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    // utilisateur spécifique (optionnel)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    // 👇 role en String
    @Column(length = 20)
    private String role; // "ADMIN", "AGENT", "CITOYEN"



    @Column(name = "entity_id")
    private Long entityId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}