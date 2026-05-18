package com.vivaeventos.ticketservice.dto;

import jakarta.validation.constraints.NotBlank;

public record ValidateTicketRequest(
        @NotBlank(message = "El código no puede estar vacío")
        String codigo
) {}
