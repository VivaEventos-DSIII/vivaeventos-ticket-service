-- V4__add_audit_log.sql
-- US-17: Trazabilidad de acciones relevantes del ticket-service

CREATE TABLE audit_log (
                           id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           actor         VARCHAR(255)  NOT NULL, -- 'SYSTEM', 'KAFKA', o ID de usuario
                           action        VARCHAR(100)  NOT NULL, -- 'TICKET_GENERATED', 'TICKET_VALIDATED', etc.
                           entity_type   VARCHAR(100)  NOT NULL, -- 'TICKET', 'TICKET_VALIDATION'
                           entity_id     VARCHAR(255),           -- UUID de la entidad afectada
                           detail        TEXT,                   -- JSON o texto libre con contexto adicional
                           result        VARCHAR(50)   NOT NULL, -- 'SUCCESS', 'FAILURE', 'DUPLICATE'
                           occurred_at   TIMESTAMP     NOT NULL DEFAULT now()
);

CREATE INDEX idx_audit_log_action      ON audit_log(action);
CREATE INDEX idx_audit_log_entity_id   ON audit_log(entity_id);
CREATE INDEX idx_audit_log_occurred_at ON audit_log(occurred_at);