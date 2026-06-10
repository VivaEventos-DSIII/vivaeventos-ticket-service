package com.vivaeventos.ticketservice.kafka;

import com.vivaeventos.ticketservice.event.PagoConfirmadoEvent;
import com.vivaeventos.ticketservice.module.Ticket;
import com.vivaeventos.ticketservice.service.AuditLogService;
import com.vivaeventos.ticketservice.service.TicketService;
import java.util.List;
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
    private final AuditLogService auditLogService;

    @KafkaListener(
            topics = "${kafka.topics.order-confirmed:order.confirmed}",
            groupId = "ticket-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onPagoConfirmado(PagoConfirmadoEvent evento) {
        log.info("order.confirmed recibido para orden {}", evento.orderId());
        try {
            List<Ticket> tickets = ticketService.generarTickets(evento);
            publisher.publishTicketGenerated(tickets, evento);
            log.info("{} ticket(s) generados y publicados para orden {}", tickets.size(), evento.orderId());
        } catch (Exception e) {
            auditLogService.registrar("KAFKA", "TICKET_GENERATED",
                    "TICKET", evento.orderId().toString(),
                    "Error procesando orden: " + e.getMessage(), "FAILURE");
            throw e;
        }
    }
}