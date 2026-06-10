package com.vivaeventos.ticketservice.kafka;

import com.vivaeventos.ticketservice.event.PagoConfirmadoEvent;
import com.vivaeventos.ticketservice.event.TicketGeneradoEvent;
import com.vivaeventos.ticketservice.module.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.ticket-generated:ticket.generated}")
    private String ticketGeneratedTopic;

    public void publishTicketGenerated(List<Ticket> tickets, PagoConfirmadoEvent origen) {
        List<String> codes = tickets.stream().map(Ticket::getUniqueCode).toList();
        TicketGeneradoEvent event = new TicketGeneradoEvent(
                origen.orderId(),
                origen.eventId(),
                origen.userId(),
                origen.userEmail(),
                origen.userName(),
                origen.eventName(),
                origen.eventDate(),
                origen.venue(),
                codes
        );
        kafkaTemplate.send(ticketGeneratedTopic, origen.orderId().toString(), event);
        log.info("Evento ticket.generated publicado para orden {} con {} tickets",
                origen.orderId(), codes.size());
    }
}
