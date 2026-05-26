package com.example.backend.dto;

import lombok.Data;

@Data
public class AgentDTO {

    private String name;
    private String prenom;
    private String email;
    private String phone;
    private String password;

    private String bureau;
    private String status;
    private String role;

    private Long departementId;
}