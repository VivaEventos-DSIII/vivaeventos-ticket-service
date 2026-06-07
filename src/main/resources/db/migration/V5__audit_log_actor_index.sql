-- US-17: índice en actor para consultas por emisor de la acción
CREATE INDEX idx_audit_log_actor ON audit_log(actor);
