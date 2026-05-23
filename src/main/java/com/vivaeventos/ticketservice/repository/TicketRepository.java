package com.vivaeventos.ticketservice.repository;

import com.vivaeventos.ticketservice.module.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Ticket> findByUniqueCode(String uniqueCode);

    Optional<Ticket> findByOrderId(UUID orderId);
}