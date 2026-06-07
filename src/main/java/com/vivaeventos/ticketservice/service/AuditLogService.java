package com.vivaeventos.ticketservice.service;

import com.vivaeventos.ticketservice.module.AuditLog;
import com.vivaeventos.ticketservice.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void registrar(String actor, String action,
                          String entityType, String entityId,
                          String detail, String result) {
        try {
            AuditLog entry = AuditLog.builder()
                    .actor(actor)
                    .action(action)
                    .entityType(entityType)
                    .entityId(entityId)
                    .detail(detail)
                    .result(result)
                    .occurredAt(LocalDateTime.now())
                    .build();
            auditLogRepository.save(entry);
        } catch (Exception e) {
            // El fallo de auditoría NO debe interrumpir el flujo principal
            log.error("Error al registrar auditoría [{}:{}]: {}", action, entityId, e.getMessage());
        }
    }
}