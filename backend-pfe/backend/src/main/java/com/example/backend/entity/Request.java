package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;        // Demande, Réclamation...
    private String description;
    private String status;

    private LocalDate date;

    // =========================
    // QUI POSE LA DEMANDE
    // =========================
    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private User citizen;

    // =========================
    // QUI TRAITE LA DEMANDE
    // =========================
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}