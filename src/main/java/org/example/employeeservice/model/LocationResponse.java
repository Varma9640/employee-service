package org.example.employeeservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LocationResponse {
    private Long locationId;
    private String locationName;
    private String city;
    private String state;
    private String country;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
}
