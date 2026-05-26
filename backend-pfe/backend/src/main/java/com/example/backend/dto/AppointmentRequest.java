package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AppointmentRequest {

    private Long id;
    private String ticket;

    private Long citizenId;   // ✔️ obligatoire

    private String agentName;
    private String ServiceName;
    private String bureau;
    private String citizenName; // optionnel (affichage frontend)
    private Long agentId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDateTime;
    private LocalDate date;
    private String time;
    private String notes;
    private String status;
    private Long departmentId;
    private Integer queueNumber;
    private Integer peopleAhead;

    // --- File d'attente ---
    private Integer queuePosition;    // FIX — frontend lit queuePosition
    private Integer currentServing;   // déjà présent
    private String  urgency;          // "urgent" | "soon" | "normal"
    private Integer diffMinutes;      // minutes restantes avant le RDV

    // --- Ticket / numéro citoyen ---
    private String ticketNumber;      // FIX — frontend lit ticketNumber
    private String citizenNumber;     // FIX — frontend lit citizenNumber

    // --- Localisation ---
    private String location;

}