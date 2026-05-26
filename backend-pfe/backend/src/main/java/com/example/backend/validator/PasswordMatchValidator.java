package com.example.backend.validator;

import com.example.backend.dto.ResetPasswordRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, ResetPasswordRequest> {
    @Override
    public boolean isValid(ResetPasswordRequest request, ConstraintValidatorContext context) {
        return Objects.equals(request.getPassword(), request.getConfirmPassword());
    }
}