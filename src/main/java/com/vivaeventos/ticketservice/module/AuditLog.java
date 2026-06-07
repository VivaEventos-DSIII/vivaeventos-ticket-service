package com.vivaeventos.ticketservice.module;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_log")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String actor;        // "SYSTEM", "KAFKA", o ID de usuario

    @Column(nullable = false)
    private String action;       // "TICKET_GENERATED", "TICKET_VALIDATED", etc.

    @Column(name = "entity_type", nullable = false)
    private String entityType;   // "TICKET", "TICKET_VALIDATION"

    @Column(name = "entity_id")
    private String entityId;     // UUID de la entidad afectada

    @Column(columnDefinition = "TEXT")
    private String detail;       // contexto adicional en texto libre

    @Column(nullable = false)
    private String result;       // "SUCCESS", "FAILURE", "DUPLICATE"

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;
}