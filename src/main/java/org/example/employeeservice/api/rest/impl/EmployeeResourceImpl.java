package org.example.employeeservice.api.rest.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.employeeservice.api.EmployeeResource;
import org.example.employeeservice.domain.service.EmployeeService;
import org.example.employeeservice.model.EmployeeRequest;
import org.example.employeeservice.model.EmployeeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmployeeResourceImpl implements EmployeeResource {
    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<EmployeeResponse> createEmployee(EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.createEmployee(request));
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployee(Long employeeId) {
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

    @Override
    public ResponseEntity<List<EmployeeResponse>>
    getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Override
    public ResponseEntity<EmployeeResponse> updateEmployee(Long employeeId, EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, request));
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent()
                .build();
    }
}
