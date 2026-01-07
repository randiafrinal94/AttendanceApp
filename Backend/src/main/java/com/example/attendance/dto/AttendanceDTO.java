package com.example.attendance.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendanceDTO {
    private Long id;
    private String username;
    private String name;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private LocalDate date;
}
