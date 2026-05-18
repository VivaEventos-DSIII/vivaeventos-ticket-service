package com.vivaeventos.ticketservice.dto;

import com.vivaeventos.ticketservice.module.Ticket;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketResponse(
        UUID id,
        UUID orderId,
        UUID eventId,
        UUID customerId,
        String ticketType,
        String uniqueCode,
        String qrImageUrl,
        String status,
        LocalDateTime generatedAt
) {
    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getOrderId(),
                ticket.getEventId(),
                ticket.getCustomerId(),
                ticket.getTicketType(),
                ticket.getUniqueCode(),
                ticket.getQrImageUrl(),
                ticket.getStatus(),
                ticket.getGeneratedAt()
        );
    }
}