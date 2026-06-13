package org.example.employeeservice.exception;

public class AuditProcessingException  extends RuntimeException {
    public AuditProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
