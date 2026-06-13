package org.example.employeeservice.domain.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.employeeservice.domain.service.AuditService;
import org.example.employeeservice.event.EmployeeCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeAuditConsumer {
    private final AuditService auditService;

    @KafkaListener(topics = "${kafka.topic.employee-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(EmployeeCreatedEvent event) {
        log.info("Employee event received : {}", event.getEmployeeId());
        auditService.saveAudit(event);
    }
}
