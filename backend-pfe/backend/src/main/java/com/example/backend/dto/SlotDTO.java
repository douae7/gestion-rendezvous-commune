package com.example.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class SlotDTO {
    public String time;
    public Long agentId;
    public String agentName;


}