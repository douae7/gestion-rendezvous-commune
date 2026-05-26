package com.example.backend.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CongeRequest {

    private Long agentId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
}