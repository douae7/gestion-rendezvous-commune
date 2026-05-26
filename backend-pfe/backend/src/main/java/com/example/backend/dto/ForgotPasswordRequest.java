package com.example.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    @Email(message = "Email invalide")
    @NotBlank(message = "Email requis")
    private String email;
}
