-- V2: ampliar qr_image_url para almacenar la imagen en base64 (~20-50 KB)
ALTER TABLE tickets ALTER COLUMN qr_image_url TYPE TEXT;
