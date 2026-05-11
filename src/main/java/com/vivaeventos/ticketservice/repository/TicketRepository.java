package com.vivaeventos.ticketservice.repository;

import com.vivaeventos.ticketservice.module.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Optional<Ticket> findByUniqueCode(String uniqueCode);
}
