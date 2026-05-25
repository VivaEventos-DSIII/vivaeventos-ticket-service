# Ticket Service — VivaEventos

Microservicio encargado de generar boletas digitales con QR único y validar el acceso en puerta (US-06).

## Requisitos previos

- Java 21 o superior
- Maven 3.9+
- Docker Desktop instalado y corriendo

## Pasos para correr el proyecto

### 1. Clonar el repositorio

```bash
git clone 
cd vivaeventos-ticket-service
```

### 2. Crear el archivo .env

Copia el archivo de ejemplo y ajusta los valores:

```bash
cp .env.example .env
```

El archivo `.env` debe tener:
DB_USER=vivaeventos
DB_PASSWORD=changeme

### 3. Levantar la infraestructura

```bash
docker compose up -d zookeeper kafka db-tickets
```

Espera 30 segundos y verifica que los tres contenedores estén **healthy**:

```bash
docker compose ps
```

Debes ver algo así:
NAME               STATUS
ticket-db          running (healthy)
ticket-kafka       running (healthy)
ticket-zookeeper   running (healthy)

### 4. Configurar las variables de entorno

**macOS / Linux:**
```bash
export DB_USER=vivaeventos
export DB_PASSWORD=changeme
```

**Windows PowerShell:**
```powershell
$env:DB_USER="vivaeventos"
$env:DB_PASSWORD="changeme"
```

### 5. Correr el servicio

```bash
mvn spring-boot:run
```

El servicio arranca en el puerto **8084**. Cuando veas esta línea en los logs el servicio está listo:
Started TicketServiceApplication
partitions assigned: [order.confirmed-0]

Flyway crea las tablas automáticamente al arrancar — no necesitas correr ningún SQL manualmente.

## Probar que funciona

**Consultar un ticket (debe devolver 404):**

```powershell
# Windows PowerShell
try {
    Invoke-RestMethod -Method GET -Uri "http://localhost:8084/api/tickets/00000000-0000-0000-0000-000000000001"
} catch {
    Write-Host "Status:" $_.Exception.Response.StatusCode
}
```

```bash
# macOS / Linux
curl -i http://localhost:8084/api/tickets/00000000-0000-0000-0000-000000000001
```

**Validar un QR inexistente (debe devolver 404):**

```bash
curl -i -X POST http://localhost:8084/api/tickets/CODIGO-INEXISTENTE/validate
```

## Apagar todo al terminar

```bash
docker compose down
```

Los datos de la base de datos quedan guardados en el volumen Docker. Si quieres resetear completamente:

```bash
docker compose down -v
```

## Notas importantes

- La base de datos corre en el puerto **5433** (no 5432) para no chocar con otros servicios.
- El servicio escucha el tópico `order.confirmed` de Kafka para generar tickets automáticamente cuando un pago es confirmado.
- Flyway aplica las migraciones automáticamente — nunca modifiques las tablas manualmente.
- Las variables `DB_USER` y `DB_PASSWORD` deben estar configuradas en la misma terminal donde corres `mvn spring-boot:run`.