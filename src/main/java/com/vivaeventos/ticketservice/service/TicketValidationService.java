package com.vivaeventos.ticketservice.service;

import com.vivaeventos.ticketservice.dto.TicketValidationResponse;

public interface TicketValidationService {
    TicketValidationResponse validar(String codigo);
}
