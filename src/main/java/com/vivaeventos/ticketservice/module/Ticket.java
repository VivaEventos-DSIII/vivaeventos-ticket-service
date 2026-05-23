package com.vivaeventos.ticketservice.module;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "tickets",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_ticket_order_id",
                        columnNames = "order_id"
                )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID orderId;

    private UUID eventId;

    private UUID customerId;

    private String ticketType;

    @Column(unique = true)
    private String uniqueCode;

    private String qrImageUrl;

    private String status;

    @Column(name = "validated_at")
    private LocalDateTime validatedAt;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;
}