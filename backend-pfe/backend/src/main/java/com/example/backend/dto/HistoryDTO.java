package com.example.backend.dto;

import lombok.Data;

@Data
public class HistoryDTO {
    private Long id;
    private String service;
    private String type;
    private String status;
    private String description;
    private String date;
}