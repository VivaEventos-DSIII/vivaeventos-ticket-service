package com.vivaeventos.ticketservice.repository;

import com.vivaeventos.ticketservice.module.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID> {}
