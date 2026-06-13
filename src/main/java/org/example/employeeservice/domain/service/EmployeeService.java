package org.example.employeeservice.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.employeeservice.client.LocationFeignClient;
import org.example.employeeservice.domain.entity.Employee;
import org.example.employeeservice.domain.producer.EmployeeProducer;
import org.example.employeeservice.domain.repository.EmployeeRepository;
import org.example.employeeservice.event.EmployeeCreatedEvent;
import org.example.employeeservice.exception.EmployeeNotFoundException;
import org.example.employeeservice.model.EmployeeRequest;
import org.example.employeeservice.model.EmployeeResponse;
import org.example.employeeservice.model.LocationRequest;
import org.example.employeeservice.model.LocationResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private static final String EMPLOYEE_NOT_FOUND = "Employee not found with id : ";
    private final EmployeeRepository employeeRepository;
    private final LocationFeignClient locationFeignClient;
    private final EmployeeProducer employeeProducer;

    public EmployeeResponse createEmployee(
            EmployeeRequest request) {
        var locationRequest = new LocationRequest();
        locationRequest.setLocationName(request.getLocationName());
        locationRequest.setCity(request.getCity());
        locationRequest.setState(request.getState());
        locationRequest.setCountry(request.getCountry());
        locationRequest.setLatitude(request.getLatitude());
        locationRequest.setLongitude(request.getLongitude());

        LocationResponse locationResponse = locationFeignClient.createLocation(locationRequest);

        Employee employee = Employee.builder()
                .employeeName(request.getEmployeeName())
                .employeeEmail(request.getEmployeeEmail())
                .locationId(locationResponse.getLocationId())
                .createDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeCreatedEvent event =
                EmployeeCreatedEvent.builder()
                        .employeeId(savedEmployee.getEmployeeId())
                        .employeeName(savedEmployee.getEmployeeName())
                        .employeeEmail(savedEmployee.getEmployeeEmail())
                        .locationId(savedEmployee.getLocationId())
                        .eventTime(LocalDateTime.now())
                        .build();

        employeeProducer.publishEmployeeCreatedEvent(event);
        return buildResponse(savedEmployee);
    }

    public EmployeeResponse getEmployee(
            Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND + employeeId));
        LocationResponse locationResponse = locationFeignClient.getLocation(employee.getLocationId());

        EmployeeResponse response =
                buildResponse(employee);
        response.setLatitude(
                locationResponse.getLatitude());
        response.setLongitude(
                locationResponse.getLongitude());
        return response;

    }

    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    public EmployeeResponse updateEmployee(
            Long employeeId,
            EmployeeRequest request) {

        Employee employee =
                employeeRepository.findById(employeeId)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        EMPLOYEE_NOT_FOUND + employeeId));

        var locationRequest = new LocationRequest();
        locationRequest.setLocationName(request.getLocationName());
        locationRequest.setCity(request.getCity());
        locationRequest.setState(request.getState());
        locationRequest.setCountry(request.getCountry());
        locationRequest.setLatitude(request.getLatitude());
        locationRequest.setLongitude(request.getLongitude());

        locationFeignClient.updateLocation(employee.getLocationId(), locationRequest);

        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeeEmail(request.getEmployeeEmail());
        employee.setModifiedDate(LocalDateTime.now());
        Employee updatedEmployee = employeeRepository.save(employee);
        return buildResponse(updatedEmployee);
    }

    public void deleteEmployee(
            Long employeeId) {

        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                EMPLOYEE_NOT_FOUND + employeeId));
        locationFeignClient.deleteLocation(employee.getLocationId());
        employeeRepository.deleteById(employeeId);
    }

    private EmployeeResponse buildResponse(
            Employee employee) {

        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getEmployeeName())
                .employeeEmail(employee.getEmployeeEmail())
                .locationId(employee.getLocationId())
                .createDate(employee.getCreateDate())
                .modifiedDate(employee.getModifiedDate())
                .build();
    }
}
