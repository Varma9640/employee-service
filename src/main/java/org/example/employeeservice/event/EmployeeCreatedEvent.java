package org.example.employeeservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreatedEvent implements Serializable {
    private Long employeeId;
    private String employeeName;
    private String employeeEmail;
    private Long locationId;
    private LocalDateTime eventTime;
}
