package org.example.employeeservice.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.employeeservice.BaseTestClass;
import org.example.employeeservice.domain.entity.EmployeeAudit;
import org.example.employeeservice.domain.repository.AuditRepository;
import org.example.employeeservice.event.EmployeeCreatedEvent;
import org.example.employeeservice.exception.AuditProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class AuditServiceTest extends BaseTestClass {
    @Mock
    private AuditRepository auditRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AuditService auditService;

    private EmployeeCreatedEvent employeeCreatedEvent;

    @BeforeEach
    void setUp() {
        reset(auditRepository, objectMapper);
        employeeCreatedEvent = EmployeeCreatedEvent.builder()
                .employeeId(1L)
                .employeeName("Varma")
                .employeeEmail("varma@test.com")
                .locationId(1L)
                .eventTime(LocalDateTime.now())
                .build();
    }

    @Test
    void saveAuditSuccess() throws Exception {

        when(objectMapper.writeValueAsString(employeeCreatedEvent)).thenReturn("{\"employeeId\":1}");

        auditService.saveAudit(employeeCreatedEvent);

        verify(objectMapper, times(1)).writeValueAsString(employeeCreatedEvent);
        verify(auditRepository, times(1)).save(any(EmployeeAudit.class));
    }

    @Test
    void saveAuditJsonProcessingException() throws Exception {

        when(objectMapper.writeValueAsString(employeeCreatedEvent))
                .thenThrow(new JsonProcessingException("JSON Error") {});

        AuditProcessingException exception = assertThrows(AuditProcessingException.class,
                        () -> auditService.saveAudit(employeeCreatedEvent)
                );

        assertEquals("Error while processing audit", exception.getMessage());
        verify(auditRepository, never()).save(any(EmployeeAudit.class));
    }
}
