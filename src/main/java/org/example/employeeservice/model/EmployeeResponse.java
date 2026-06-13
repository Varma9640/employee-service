package org.example.employeeservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EmployeeResponse {
    private long employeeId;
    private String employeeName;
    private String employeeEmail;
    private Long locationId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
}
