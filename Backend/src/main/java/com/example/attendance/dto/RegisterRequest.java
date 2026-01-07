package com.example.attendance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String username; // Using partial email or unique ID as username

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private String position;
}
