package com.vivaeventos.ticketservice.controller;

import com.vivaeventos.ticketservice.dto.AuditLogResponse;
import com.vivaeventos.ticketservice.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLogResponse>> listar(
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String entityId,
            @RequestParam(required = false) String result,
            @RequestParam(required = false) String actor) {
        return ResponseEntity.ok(auditLogService.listar(action, entityId, result, actor));
    }
}
