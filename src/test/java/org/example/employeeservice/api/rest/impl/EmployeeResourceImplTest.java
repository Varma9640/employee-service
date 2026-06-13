package org.example.employeeservice.api.rest.impl;

import org.example.employeeservice.BaseTestClass;
import org.example.employeeservice.domain.service.EmployeeService;
import org.example.employeeservice.model.EmployeeRequest;
import org.example.employeeservice.model.EmployeeResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class EmployeeResourceImplTest extends BaseTestClass {
    @InjectMocks
    private EmployeeResourceImpl employeeResource;

    @Mock
    private EmployeeService employeeService;

    @Test
    public void createEmployeeTest() {

        when(employeeService.createEmployee(any())).thenReturn(getEmployeeResponse());

        ResponseEntity<EmployeeResponse> response = employeeResource.createEmployee(getEmployeeRequest());

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getEmployeeTest() {

        when(employeeService.getEmployee(1L)).thenReturn(getEmployeeResponse());

        ResponseEntity<EmployeeResponse> response = employeeResource.getEmployee(1L);

        assertNotNull(response);
        assertEquals(1L, response.getBody().getEmployeeId());
    }

    @Test
    public void getAllEmployeesTest() {

        when(employeeService.getAllEmployees()).thenReturn(List.of(getEmployeeResponse()));

        ResponseEntity<List<EmployeeResponse>> response = employeeResource.getAllEmployees();

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void updateEmployeeTest() {

        when(employeeService.updateEmployee(anyLong(), any(EmployeeRequest.class))).thenReturn(getEmployeeResponse());

        ResponseEntity<EmployeeResponse> response = employeeResource.updateEmployee(1L, getEmployeeRequest());

        assertNotNull(response);
        assertEquals(1L, response.getBody().getEmployeeId());
    }

    @Test
    public void deleteEmployeeTest() {

        doNothing().when(employeeService).deleteEmployee(1L);

        ResponseEntity<Void> response = employeeResource.deleteEmployee(1L);

        assertEquals(204, response.getStatusCodeValue());
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

    private EmployeeResponse getEmployeeResponse() {

        return EmployeeResponse.builder()
                .employeeId(1L)
                .employeeName("Varma")
                .employeeEmail("varma@gmail.com")
                .locationId(100L)
                .createDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }
}
