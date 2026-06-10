package com.vivaeventos.ticketservice.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record PagoConfirmadoEvent(
        UUID orderId,
        UUID eventId,
        UUID userId,
        String ticketType,
        String transactionId,
        String userEmail,
        String userName,
        int quantity,
        String eventName,
        LocalDateTime eventDate,
        String venue
) {}
