package org.example.employeeservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Value("${kafka.topic.employee-created}")
    private String employeeCreatedTopic;

    @Bean
    public NewTopic employeeCreatedTopic() {
        return TopicBuilder
                .name(employeeCreatedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
