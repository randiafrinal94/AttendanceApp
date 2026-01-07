package com.example.attendance.controller;

import com.example.attendance.dto.AttendanceDTO;
import com.example.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(Principal principal) {
        return ResponseEntity.ok(attendanceService.checkIn(principal.getName()));
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut(Principal principal) {
        return ResponseEntity.ok(attendanceService.checkOut(principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }
}
