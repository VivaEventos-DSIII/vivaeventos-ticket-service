package com.vivaeventos.ticketservice.service.impl;

import com.vivaeventos.ticketservice.dto.TicketValidationResponse;
import com.vivaeventos.ticketservice.service.TicketService;
import com.vivaeventos.ticketservice.service.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketValidationServiceImpl implements TicketValidationService {

    private final TicketService ticketService;

    @Override
    public TicketValidationResponse validar(String codigo) {
        return ticketService.validarTicketDto(codigo);
    }
}