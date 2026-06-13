package org.example.employeeservice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "EMPLOYEE_AUDIT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;
    private Long employeeId;
    private String action;
    private LocalDateTime eventTime;
    @Column(length = 5000)
    private String payload;
}
