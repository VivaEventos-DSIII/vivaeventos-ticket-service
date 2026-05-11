package com.vivaeventos.ticketservice.controller;

import com.vivaeventos.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/validar/{codigo}")
    public ResponseEntity<Map<String, Object>> validar(@PathVariable String codigo) {
        Map<String, Object> resultado = ticketService.validarTicket(codigo);
        int status = switch ((String) resultado.get("resultado")) {
            case "VALID"       -> 200;
            case "NOT_FOUND"   -> 404;
            default            -> 409;  // ALREADY_USED | CANCELLED
        };
        return ResponseEntity.status(status).body(resultado);
    }
}
