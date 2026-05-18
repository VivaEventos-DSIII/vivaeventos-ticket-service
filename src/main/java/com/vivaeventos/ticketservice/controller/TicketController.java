package com.vivaeventos.ticketservice.controller;

import com.vivaeventos.ticketservice.dto.TicketResponse;
import com.vivaeventos.ticketservice.dto.TicketValidationResponse;
import com.vivaeventos.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    // US-06 criterio 2: consultar boleta con su identificador único
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    // RQ-05: validar QR en puerta — no usar dos veces
    @PostMapping("/{codigo}/validate")
    public ResponseEntity<TicketValidationResponse> validate(@PathVariable String codigo) {
        return ResponseEntity.ok(ticketService.validarTicketDto(codigo));
    }
}
