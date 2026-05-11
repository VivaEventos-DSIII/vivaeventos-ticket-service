package com.vivaeventos.ticketservice.event;

import java.util.UUID;

public record PagoConfirmadoEvent(
        UUID orderId,
        UUID eventId,
        UUID customerId,
        String ticketType,
        String transactionId
) {}
