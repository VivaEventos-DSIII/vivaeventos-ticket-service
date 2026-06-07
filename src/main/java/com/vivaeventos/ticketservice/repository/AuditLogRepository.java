package com.vivaeventos.ticketservice.repository;

import com.vivaeventos.ticketservice.module.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

    @Query("SELECT a FROM AuditLog a WHERE " +
            "(:action IS NULL OR a.action = :action) AND " +
            "(:entityId IS NULL OR a.entityId = :entityId) AND " +
            "(:result IS NULL OR a.result = :result) AND " +
            "(:actor IS NULL OR a.actor = :actor) " +
            "ORDER BY a.occurredAt DESC")
    List<AuditLog> findWithFilters(
            @Param("action") String action,
            @Param("entityId") String entityId,
            @Param("result") String result,
            @Param("actor") String actor);
}