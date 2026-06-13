package org.example.employeeservice.domain.repository;

import org.example.employeeservice.domain.entity.EmployeeAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<EmployeeAudit, Long> {
}
