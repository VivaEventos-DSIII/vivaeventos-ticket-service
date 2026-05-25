package com.vivaeventos.ticketservice.kafka;

import com.vivaeventos.ticketservice.event.TicketGeneradoEvent;
import com.vivaeventos.ticketservice.module.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.ticket-generated:ticket.generated}")
    private String ticketGeneratedTopic;

    public void publishTicketGenerated(Ticket ticket) {
        TicketGeneradoEvent event = new TicketGeneradoEvent(
                ticket.getId(),
                ticket.getOrderId(),
                ticket.getEventId(),
                ticket.getCustomerId(),
                ticket.getUniqueCode(),
                ticket.getTicketType()
        );
        kafkaTemplate.send(ticketGeneratedTopic, ticket.getId().toString(), event);
        log.info("Evento ticket.generated publicado para ticket {}", ticket.getId());
    }
}
