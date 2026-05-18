package com.vivaeventos.ticketservice.exception;

public class TicketAlreadyUsedException extends RuntimeException {

    public TicketAlreadyUsedException(String codigo) {
        super("El ticket ya fue utilizado: " + codigo);
    }
}
