package com.example.backend.dto;

import lombok.Data;

@Data
public class ReminderRequest {
    private Long appointmentId;
    private String email;
    private String name;
    private String service;
    private String date;
    private String time;
}