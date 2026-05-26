package com.example.backend.dto;

import com.example.backend.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private String title;
    private String message;
    private NotificationType type;
    private LocalDateTime date;
}