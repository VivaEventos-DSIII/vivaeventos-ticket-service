package com.vivaeventos.ticketservice.service;

import com.vivaeventos.ticketservice.dto.AuditLogResponse;
import com.vivaeventos.ticketservice.module.AuditLog;
import com.vivaeventos.ticketservice.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
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

    public List<AuditLogResponse> listar(String action, String entityId, String result, String actor) {
        return auditLogRepository.findWithFilters(action, entityId, result, actor)
                .stream()
                .map(AuditLogResponse::from)
                .toList();
    }
}