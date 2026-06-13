package org.example.employeeservice.client;

import org.example.employeeservice.model.LocationRequest;
import org.example.employeeservice.model.LocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "location-service",
        url = "http://localhost:8081")
public interface LocationFeignClient {
    @GetMapping("/locations/location-details/{locationId}")
    LocationResponse getLocation(
            @PathVariable Long locationId);

    @PostMapping("/locations/create")
    LocationResponse createLocation(
            @RequestBody LocationRequest request);

    @PutMapping("/locations/update-location-details/{locationId}")
    LocationResponse updateLocation(
            @PathVariable Long locationId,
            @RequestBody LocationRequest request);

    @DeleteMapping("/locations/delete-location-details/{locationId}")
    void deleteLocation(
            @PathVariable Long locationId);
}
