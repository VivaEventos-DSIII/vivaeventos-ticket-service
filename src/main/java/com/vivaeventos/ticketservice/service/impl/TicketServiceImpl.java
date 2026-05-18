package com.vivaeventos.ticketservice.service.impl;

import com.vivaeventos.ticketservice.dto.TicketResponse;
import com.vivaeventos.ticketservice.dto.TicketValidationResponse;
import com.vivaeventos.ticketservice.event.PagoConfirmadoEvent;
import com.vivaeventos.ticketservice.exception.TicketAlreadyUsedException;
import com.vivaeventos.ticketservice.exception.TicketNotFoundException;
import com.vivaeventos.ticketservice.module.Ticket;
import com.vivaeventos.ticketservice.module.TicketValidation;
import com.vivaeventos.ticketservice.repository.TicketRepository;
import com.vivaeventos.ticketservice.repository.TicketValidationRepository;
import com.vivaeventos.ticketservice.service.QrService;
import com.vivaeventos.ticketservice.service.TicketService;
import com.vivaeventos.ticketservice.util.UniqueCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketValidationRepository validationRepository;
    private final QrService qrService;

    @Override
    @Transactional
    public Ticket generarTicket(PagoConfirmadoEvent evento) {
        return ticketRepository.findByOrderId(evento.orderId())
                .orElseGet(() -> {
                    String uniqueCode = UniqueCodeGenerator.generate();
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
                    Ticket saved = ticketRepository.save(ticket);
                    log.info("Ticket generado: {} para orden {}", uniqueCode, evento.orderId());
                    return saved;
                });
    }

    @Override
    public TicketResponse getTicketById(UUID id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
        return TicketResponse.from(ticket);
    }

    @Override
    @Transactional
    public TicketValidationResponse validarTicketDto(String codigo) {
        Ticket ticket = ticketRepository.findByUniqueCode(codigo)
                .orElseThrow(() -> new TicketNotFoundException(codigo));

        if ("USED".equals(ticket.getStatus())) {
            throw new TicketAlreadyUsedException(codigo);
        }

        ticket.setStatus("USED");
        ticket.setValidatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);

        validationRepository.save(TicketValidation.builder()
                .ticket(ticket)
                .result("VALID")
                .validatedAt(LocalDateTime.now())
                .offlineMode(false)
                .build());

        return new TicketValidationResponse(codigo, "VALID", ticket.getId());
    }
}
