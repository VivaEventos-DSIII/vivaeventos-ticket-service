package com.vivaeventos.ticketservice.dto;

import com.vivaeventos.ticketservice.module.AuditLog;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuditLogResponse(
        UUID id,
        String actor,
        String action,
        String entityType,
        String entityId,
        String detail,
        String result,
        LocalDateTime occurredAt
) {
    public static AuditLogResponse from(AuditLog log) {
        return new AuditLogResponse(
                log.getId(),
                log.getActor(),
                log.getAction(),
                log.getEntityType(),
                log.getEntityId(),
                log.getDetail(),
                log.getResult(),
                log.getOccurredAt()
        );
    }
}
