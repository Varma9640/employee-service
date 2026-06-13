package org.example.employeeservice.domain.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.employeeservice.domain.entity.EmployeeAudit;
import org.example.employeeservice.domain.repository.AuditRepository;
import org.example.employeeservice.event.EmployeeCreatedEvent;
import org.example.employeeservice.exception.AuditProcessingException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {
    private static final String EMPLOYEE_CREATED = "EMPLOYEE_CREATED";
    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;

    public void saveAudit(EmployeeCreatedEvent event) {
        try {
            EmployeeAudit audit = EmployeeAudit.builder()
                            .employeeId(event.getEmployeeId())
                            .action(EMPLOYEE_CREATED)
                            .eventTime(event.getEventTime())
                            .payload(objectMapper.writeValueAsString(event))
                            .build();
            auditRepository.save(audit);
            log.info("Audit record saved for employee : {}", event.getEmployeeId());
        } catch (JsonProcessingException exception) {
            log.error("Error while saving audit record", exception);
            throw new AuditProcessingException("Error while processing audit", exception);
        }
    }
}
