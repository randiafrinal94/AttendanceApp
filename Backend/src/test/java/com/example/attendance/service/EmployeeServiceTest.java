package com.example.attendance.service;

import com.example.attendance.dto.EmployeeRequest;
import com.example.attendance.entity.Employee;
import com.example.attendance.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    @SuppressWarnings("null")
    public void testCreateEmployee() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setName("John Doe");
        request.setPosition("Developer");

        Employee savedEmployee = new Employee();
        savedEmployee.setId(1L);
        savedEmployee.setName("John Doe");
        savedEmployee.setPosition("Developer");

        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        // Act
        Employee result = employeeService.createEmployee(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("Developer", result.getPosition());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    public void testGetAllEmployees() {
        // Arrange
        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setName("Alice");

        Employee emp2 = new Employee();
        emp2.setId(2L);
        emp2.setName("Bob");

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        // Act
        List<Employee> result = employeeService.getAllEmployees();

        // Assert
        assertEquals(2, result.size());
        verify(employeeRepository).findAll();
    }
}
