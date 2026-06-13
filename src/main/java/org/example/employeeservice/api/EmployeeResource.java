package org.example.employeeservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.employeeservice.api.swagger.EmployeeResponseSwagger;
import org.example.employeeservice.model.EmployeeRequest;
import org.example.employeeservice.model.EmployeeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Valid
@EmployeeResponseSwagger
@Tag(name = "Employee Resource",
        description = "Employee CRUD Operations")
@RequestMapping(value = "/employees", produces = APPLICATION_JSON_VALUE)
public interface EmployeeResource {
    @Operation(
            summary = "This API creates employee details",
            description = "This API creates employee details in database")
    @Valid
    @PostMapping(path = "/create")
    ResponseEntity<EmployeeResponse> createEmployee(
            @RequestBody EmployeeRequest request);

    @Operation(
            summary = "This API fetches employee details",
            description = "This API fetches employee details using employee id")
    @Valid
    @GetMapping(path = "/employee-details/{employeeId}")
    ResponseEntity<EmployeeResponse> getEmployee(
            @PathVariable Long employeeId);

    @Operation(
            summary = "This API fetches all employee details",
            description = "This API fetches all employee records from database")
    @Valid
    @GetMapping(path = "/all-employee-details")
    ResponseEntity<List<EmployeeResponse>> getAllEmployees();

    @Operation(
            summary = "This API updates employee details",
            description = "This API updates employee details using employee id")
    @Valid
    @PutMapping(path = "/update-employee-details/{employeeId}")
    ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long employeeId,
            @RequestBody EmployeeRequest request);

    @Operation(
            summary = "This API deletes employee details",
            description = "This API deletes employee details using employee id")
    @Valid
    @DeleteMapping(path = "/delete-employee-details/{employeeId}")
    ResponseEntity<Void> deleteEmployee(
            @PathVariable Long employeeId);
}
