package org.example.employeeservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeRequest {
    @NotBlank(message = "Employee name is required")
    private String employeeName;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Employee email is required")
    private String employeeEmail;
    @NotBlank(message = "Location Name is mandatory")
    private String locationName;
    @NotBlank(message = "City is mandatory")
    private String city;
    @NotBlank(message = "State is mandatory")
    private String state;
    @NotBlank(message = "Country is mandatory")
    private String country;
    private Double latitude;
    private Double longitude;
}
