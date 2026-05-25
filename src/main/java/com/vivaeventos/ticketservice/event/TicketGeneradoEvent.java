package com.vivaeventos.ticketservice.event;

import java.util.UUID;

public record TicketGeneradoEvent(
        UUID ticketId,
        UUID orderId,
        UUID eventId,
        UUID customerId,
        String uniqueCode,
        String ticketType
) {}