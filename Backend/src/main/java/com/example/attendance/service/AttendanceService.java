package com.example.attendance.service;

import com.example.attendance.dto.AttendanceDTO;
import com.example.attendance.entity.Attendance;
import com.example.attendance.entity.Employee;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Attendance checkIn(String username) {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();
        Optional<Attendance> existing = attendanceRepository.findByEmployeeAndDate(employee, today);

        if (existing.isPresent()) {
            throw new RuntimeException("Already checked in for today");
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setDate(today);

        return attendanceRepository.save(attendance);
    }

    public Attendance checkOut(String username) {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByEmployeeAndDate(employee, today)
                .orElseThrow(() -> new RuntimeException("No check-in record found for today"));

        if (attendance.getCheckOutTime() != null) {
            throw new RuntimeException("Already checked out");
        }

        attendance.setCheckOutTime(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    public List<AttendanceDTO> getAllAttendance() {
        return attendanceRepository.findAllByOrderByCheckInTimeAsc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AttendanceDTO convertToDTO(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setUsername(attendance.getEmployee().getUsername());
        dto.setName(attendance.getEmployee().getName());
        dto.setCheckInTime(attendance.getCheckInTime());
        dto.setCheckOutTime(attendance.getCheckOutTime());
        dto.setDate(attendance.getDate());
        return dto;
    }
}
