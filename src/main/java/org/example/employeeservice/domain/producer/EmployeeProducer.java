package org.example.employeeservice.domain.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.example.employeeservice.event.EmployeeCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.SendResult;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeProducer {
    private final KafkaTemplate<String, EmployeeCreatedEvent> kafkaTemplate;

    @Value("${kafka.topic.employee-created}")
    private String topic;

    public void publishEmployeeCreatedEvent(EmployeeCreatedEvent event) {

        CompletableFuture<SendResult<String, EmployeeCreatedEvent>> future =
                kafkaTemplate.send(topic, String.valueOf(event.getEmployeeId()), event);
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("Employee event published successfully : {}", event.getEmployeeId());
            } else {
                log.error("Failed to publish employee event : {}", event.getEmployeeId(), exception);
            }
        });
    }
}
