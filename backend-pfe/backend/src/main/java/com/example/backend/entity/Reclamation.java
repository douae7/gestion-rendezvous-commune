package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String message;
    private String status; // OPEN, IN_PROGRESS, RESOLVED

    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Appointment appointment;

}