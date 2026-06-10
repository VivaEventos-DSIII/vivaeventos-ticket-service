-- V6: Remove unique constraint on order_id to allow multiple tickets per order
ALTER TABLE tickets DROP CONSTRAINT IF EXISTS uq_tickets_order_id;
