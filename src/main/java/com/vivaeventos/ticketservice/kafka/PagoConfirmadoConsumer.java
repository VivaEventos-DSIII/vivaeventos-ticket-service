package com.vivaeventos.ticketservice.kafka;

import com.vivaeventos.ticketservice.event.PagoConfirmadoEvent;
import com.vivaeventos.ticketservice.module.Ticket;
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
    private final TicketEventPublisher publisher;

    @KafkaListener(
            topics = "${kafka.topics.order-confirmed:order.confirmed}",
            groupId = "ticket-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onPagoConfirmado(PagoConfirmadoEvent evento) {

        try {

            log.info(
                    "order.confirmed recibido para orden {}",
                    evento.orderId()
            );

            Ticket ticket = ticketService.generarTicket(evento);

            publisher.publishTicketGenerated(ticket);

            log.info(
                    "Ticket {} generado y publicado",
                    ticket.getUniqueCode()
            );

        } catch (Exception e) {

            log.error(
                    "Error procesando orden {}",
                    evento.orderId(),
                    e
            );

            throw e;
        }
    }
}