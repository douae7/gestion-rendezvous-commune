package com.example.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// PasswordResetToken.java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    @Id
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime expiryDate;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
