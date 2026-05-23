package com.vivaeventos.ticketservice.module;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ticket_validations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketValidation {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(name = "validated_by")
    private String validatedBy;

    private String result;

    @Column(name = "validated_at")
    private LocalDateTime validatedAt;

    @Column(name = "offline_mode")
    private Boolean offlineMode;
}