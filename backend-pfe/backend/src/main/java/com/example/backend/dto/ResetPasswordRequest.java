package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public class ResetPasswordRequest {

    @NotBlank(message = "Le mot de passe est requis")
    @Size(min = 8, message = "Minimum 8 caractères")
    private String password;

    @NotBlank(message = "La confirmation est requise")
    @Size(min = 8, message = "Minimum 8 caractères")
    private String confirmPassword;

    // Getters/Setters
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    // ✅ Validation personnalisée
    public boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }
}