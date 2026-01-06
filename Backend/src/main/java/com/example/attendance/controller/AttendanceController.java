package com.example.attendance.controller;

import com.example.attendance.dto.AttendanceRequest;
import com.example.attendance.entity.Attendance;
import com.example.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(@Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.checkIn(request.getEmployeeId()));
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut(@Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.checkOut(request.getEmployeeId()));
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }
}
