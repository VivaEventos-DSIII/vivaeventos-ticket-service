-- V3: UNIQUE constraint e índice en order_id para idempotencia en generarTicket (RQ-06)
ALTER TABLE tickets ADD CONSTRAINT uq_tickets_order_id UNIQUE (order_id);
CREATE INDEX idx_tickets_order_id ON tickets(order_id);
