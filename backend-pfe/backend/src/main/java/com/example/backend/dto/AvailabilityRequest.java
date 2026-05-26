package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityRequest {

    private String day;

    private LocalTime startTime;

    private boolean available;

    private Long agentId;
}