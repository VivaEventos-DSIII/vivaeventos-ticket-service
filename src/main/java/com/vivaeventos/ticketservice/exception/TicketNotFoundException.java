package com.vivaeventos.ticketservice.exception;

import java.util.UUID;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(String codigo) {
        super("Ticket no encontrado con código: " + codigo);
    }

    public TicketNotFoundException(UUID id) {
        super("Ticket no encontrado con id: " + id);
    }
}