package com.example.attendance.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttendanceRequest {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;
}
