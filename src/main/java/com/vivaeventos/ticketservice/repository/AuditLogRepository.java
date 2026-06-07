package com.vivaeventos.ticketservice.repository;

import com.vivaeventos.ticketservice.module.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
}