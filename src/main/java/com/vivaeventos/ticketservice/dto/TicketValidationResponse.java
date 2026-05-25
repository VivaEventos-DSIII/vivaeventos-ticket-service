package com.vivaeventos.ticketservice.dto;

import java.util.UUID;

public record TicketValidationResponse(
        String codigo,
        String resultado,
        UUID ticketId
) {}