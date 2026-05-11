package com.vivaeventos.ticketservice.kafka;

import com.vivaeventos.ticketservice.module.Ticket;
import com.vivaeventos.ticketservice.event.PagoConfirmadoEvent;
import com.vivaeventos.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagoConfirmadoConsumer {

    private final TicketService ticketService;

    @KafkaListener(topics = "pago-confirmado", groupId = "ticket-service-group")
    public void onPagoConfirmado(PagoConfirmadoEvent evento) {
        log.info("Pago confirmado recibido para orden {}", evento.orderId());
        Ticket ticket = ticketService.generarTicket(evento);
        log.info("Ticket generado: {} para orden {}", ticket.getUniqueCode(), evento.orderId());
    }
}
