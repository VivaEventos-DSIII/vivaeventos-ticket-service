ALTER TABLE tickets
ADD CONSTRAINT uk_ticket_order_id UNIQUE(order_id);

CREATE INDEX idx_tickets_order_id ON tickets(order_id);