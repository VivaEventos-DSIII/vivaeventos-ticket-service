package com.vivaeventos.ticketservice.service;

import com.vivaeventos.ticketservice.module.Ticket;
import com.vivaeventos.ticketservice.module.TicketValidation;
import com.vivaeventos.ticketservice.event.PagoConfirmadoEvent;
import com.vivaeventos.ticketservice.repository.TicketRepository;
import com.vivaeventos.ticketservice.repository.TicketValidationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketValidationRepository validationRepository;
    private final QrService qrService;

    @Transactional
    public Ticket generarTicket(PagoConfirmadoEvent evento) {
        String uniqueCode = UUID.randomUUID().toString().toUpperCase().replace("-", "");

        Ticket ticket = Ticket.builder()
                .orderId(evento.orderId())
                .eventId(evento.eventId())
                .customerId(evento.customerId())
                .ticketType(evento.ticketType() != null ? evento.ticketType() : "GENERAL")
                .uniqueCode(uniqueCode)
                .qrImageUrl(qrService.generateBase64(uniqueCode))
                .status("ACTIVE")
                .generatedAt(LocalDateTime.now())
                .build();

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Map<String, Object> validarTicket(String codigo) {
        return ticketRepository.findByUniqueCode(codigo)
                .map(ticket -> {
                    String result = switch (ticket.getStatus()) {
                        case "ACTIVE" -> {
                            ticket.setStatus("USED");
                            ticket.setValidatedAt(LocalDateTime.now());
                            ticketRepository.save(ticket);
                            yield "VALID";
                        }
                        case "USED" -> "ALREADY_USED";
                        default -> "CANCELLED";
                    };

                    validationRepository.save(TicketValidation.builder()
                            .ticket(ticket)
                            .result(result)
                            .validatedAt(LocalDateTime.now())
                            .offlineMode(false)
                            .build());

                    return Map.<String, Object>of(
                            "codigo", codigo,
                            "resultado", result,
                            "ticketId", ticket.getId()
                    );
                })
                .orElseGet(() -> Map.of("codigo", codigo, "resultado", "NOT_FOUND"));
    }
}
