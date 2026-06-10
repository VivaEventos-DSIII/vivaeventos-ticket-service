package com.vivaeventos.ticketservice.service;

import com.vivaeventos.ticketservice.dto.TicketResponse;
import com.vivaeventos.ticketservice.dto.TicketValidationResponse;
import com.vivaeventos.ticketservice.event.PagoConfirmadoEvent;
import com.vivaeventos.ticketservice.module.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    List<Ticket> generarTickets(PagoConfirmadoEvent evento);
    TicketResponse getTicketById(UUID id);
    TicketValidationResponse validarTicketDto(String codigo);
}
