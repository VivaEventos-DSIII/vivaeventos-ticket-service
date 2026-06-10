package com.vivaeventos.ticketservice.repository;

import com.vivaeventos.ticketservice.module.Ticket;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Ticket t WHERE t.uniqueCode = :uniqueCode")
    Optional<Ticket> findByUniqueCode(@Param("uniqueCode") String uniqueCode);

    List<Ticket> findAllByOrderId(UUID orderId);
}
