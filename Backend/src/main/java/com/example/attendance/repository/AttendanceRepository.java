package com.example.attendance.repository;

import com.example.attendance.entity.Attendance;
import com.example.attendance.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);

    // Sort by checkInTime ascending (or date)
    List<Attendance> findAllByOrderByCheckInTimeAsc();
}
