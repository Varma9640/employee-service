package org.example.employeeservice.domain.service;

import org.example.employeeservice.BaseTestClass;
import org.example.employeeservice.client.LocationFeignClient;
import org.example.employeeservice.domain.entity.Employee;
import org.example.employeeservice.domain.producer.EmployeeProducer;
import org.example.employeeservice.domain.repository.EmployeeRepository;
import org.example.employeeservice.event.EmployeeCreatedEvent;
import org.example.employeeservice.exception.EmployeeNotFoundException;
import org.example.employeeservice.model.EmployeeRequest;
import org.example.employeeservice.model.EmployeeResponse;
import org.example.employeeservice.model.LocationResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest extends BaseTestClass {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private LocationFeignClient locationFeignClient;
    @Mock
    private EmployeeProducer employeeProducer;

    @Test
    public void createEmployeeTest() {

        EmployeeRequest request = getEmployeeRequest();
        LocationResponse locationResponse = getLocationResponse();
        Employee employee = getEmployee();

        when(locationFeignClient.createLocation(any())).thenReturn(locationResponse);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        doNothing().when(employeeProducer).publishEmployeeCreatedEvent(any(EmployeeCreatedEvent.class));

        EmployeeResponse response = employeeService.createEmployee(request);

        assertNotNull(response);
        verify(employeeRepository).save(any(Employee.class));
        verify(employeeProducer).publishEmployeeCreatedEvent(any(EmployeeCreatedEvent.class));
    }

    @Test
    public void getEmployeeTest() {

        Employee employee = getEmployee();
        LocationResponse locationResponse = getLocationResponse();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(locationFeignClient.getLocation(100L)).thenReturn(locationResponse);

        EmployeeResponse response = employeeService.getEmployee(1L);

        assertNotNull(response);
        assertEquals(1L, response.getEmployeeId());
        assertEquals(17.3850, response.getLatitude());
        assertEquals(78.4867, response.getLongitude());
    }

    @Test
    public void getEmployeeNotFoundTest() {

        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(1L));
    }

    @Test
    public void getAllEmployeesTest() {

        when(employeeRepository.findAll()).thenReturn(List.of(getEmployee()));

        List<EmployeeResponse> response = employeeService.getAllEmployees();

        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    public void updateEmployeeTest() {

        Employee employee = getEmployee();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(locationFeignClient.updateLocation(anyLong(), any())).thenReturn(getLocationResponse());

        employeeService.updateEmployee(1L, getEmployeeRequest());

        verify(employeeRepository, times(2)).save(any(Employee.class));
    }

    @Test
    public void deleteEmployeeTest() {

        Employee employee = getEmployee();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).deleteById(1L);
        doNothing().when(locationFeignClient).deleteLocation(100L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }

    private EmployeeRequest getEmployeeRequest() {

        EmployeeRequest request = new EmployeeRequest();
        request.setEmployeeName("Varma");
        request.setEmployeeEmail("varma@gmail.com");
        request.setLocationName("ING");
        request.setCity("Hyderabad");
        request.setState("Telangana");
        request.setCountry("India");
        request.setLatitude(17.3850);
        request.setLongitude(78.4867);
        return request;
    }

    private Employee getEmployee() {

        return Employee.builder()
                .employeeId(1L)
                .employeeName("Varma")
                .employeeEmail("varma@gmail.com")
                .locationId(100L)
                .createDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    private LocationResponse getLocationResponse() {

        return LocationResponse.builder()
                .locationId(100L)
                .locationName("ING")
                .city("Hyderabad")
                .state("Telangana")
                .country("India")
                .latitude(17.3850)
                .longitude(78.4867)
                .createDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }
}
