# Ticket Service — VivaEventos

Microservicio encargado de generar boletas digitales con QR único y validar el acceso en puerta.

# 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd vivaeventos-ticket-service
```

---

# 2. Crear el archivo .env

## Windows PowerShell

```powershell
copy .env.example .env
```

## macOS / Linux

```bash
cp .env.example .env
```

Contenido esperado del archivo `.env`:

```env
# Base de datos
DB_USER=vivaeventos
DB_PASSWORD=changeme
```

> El archivo `.env` NO debe subirse a GitHub.

---

# 3. Levantar infraestructura Docker

Iniciar PostgreSQL + Kafka + Zookeeper:

```bash
docker compose up -d zookeeper kafka db-tickets
```

Verificar que los contenedores estén funcionando:

```bash
docker compose ps
```

Debe verse algo similar:

```text
NAME                 STATUS
ticket-db            running (healthy)
ticket-kafka         running (healthy)
ticket-zookeeper     running (healthy)
```

---

# 4. Configurar variables de entorno

## Windows PowerShell

```powershell
$env:DB_USER="vivaeventos"
$env:DB_PASSWORD="changeme"
```

## macOS / Linux

```bash
export DB_USER=vivaeventos
export DB_PASSWORD=changeme
```

> IMPORTANTE:
> Estas variables deben configurarse en la MISMA terminal donde se ejecutará Maven.

---

# 5. Ejecutar el microservicio

```bash
mvn spring-boot:run
```

El servicio iniciará en:

```text
http://localhost:8084
```

Cuando aparezca este log, el sistema está listo:

```text
Started TicketServiceApplication
```

y también:

```text
partitions assigned: [order.confirmed-0]
```

---

# Base de datos

La base de datos PostgreSQL corre dentro de Docker.

## Configuración

| Parámetro | Valor |
|---|---|
| Host | localhost |
| Puerto | 5433 |
| Database | ticketdb |
| Usuario | vivaeventos |
| Password | changeme |

---



# Migraciones Flyway

Las tablas se crean automáticamente al arrancar la aplicación.

NO ejecutar scripts SQL manualmente.

Flyway aplicará automáticamente las migraciones ubicadas en:

```text
src/main/resources/db/migration
```

---

# Probar endpoints

---

## 1. Consultar ticket inexistente

### Windows PowerShell

```powershell
try {
    Invoke-RestMethod -Method GET -Uri "http://localhost:8084/api/tickets/00000000-0000-0000-0000-000000000001"
} catch {
    Write-Host "Status:" $_.Exception.Response.StatusCode
}
```

### macOS / Linux

```bash
curl -i http://localhost:8084/api/tickets/00000000-0000-0000-0000-000000000001
```

Debe devolver:

```text
404 Not Found
```

---

## 2. Validar QR inexistente

### Windows

```powershell
curl.exe -i -X POST "http://localhost:8084/api/tickets/CODIGO-INEXISTENTE/validate"
```

### macOS / Linux

```bash
curl -i -X POST "http://localhost:8084/api/tickets/CODIGO-INEXISTENTE/validate"
```

Respuesta esperada:

```json
{
  "status": 404,
  "error": "Ticket no encontrado con código: CODIGO-INEXISTENTE"
}
```

---

# Reiniciar completamente la base de datos

Detener contenedores:

```bash
docker compose down
```

Eliminar también los datos persistidos:

```bash
docker compose down -v
```

Luego volver a iniciar:

```bash
docker compose up -d zookeeper kafka db-tickets
```

---

# Configuración de datasource

El proyecto utiliza variables de entorno para evitar hardcodear credenciales sensibles.

```yml
datasource:
  url: jdbc:postgresql://${DB_HOST:localhost}:5433/${DB_NAME:ticketdb}
  username: ${DB_USER:vivaeventos}
  password: ${DB_PASSWORD:changeme}
```

Esto permite:

- usar valores por defecto en desarrollo
- sobrescribir variables en producción
- evitar exponer secretos reales en GitHub

---

# Seguridad

- `.env` NO debe subirse al repositorio.
- `.env.example` SÍ puede subirse porque contiene valores de desarrollo.
- Las credenciales reales de producción deben manejarse mediante variables de entorno seguras.

---

# Estructura Docker

| Servicio | Contenedor |
|---|---|
| PostgreSQL | ticket-db |
| Kafka | ticket-kafka |
| Zookeeper | ticket-zookeeper |

---

# Notas importantes

- PostgreSQL usa el puerto `5433` para evitar conflictos con otros servicios locales.
- Kafka escucha eventos en el tópico:

```text
order.confirmed
```

- El microservicio genera tickets automáticamente cuando recibe un evento de pago confirmado.
- Flyway controla completamente el schema de la base de datos.
- No modificar tablas manualmente.

---

# Troubleshooting

## Error: password authentication failed

Verificar variables de entorno:

### Windows PowerShell

```powershell
echo $env:DB_USER
echo $env:DB_PASSWORD
```

### macOS / Linux

```bash
echo $DB_USER
echo $DB_PASSWORD
```

---

## Error: Connection refused localhost:5433

Verificar que PostgreSQL esté arriba:

```bash
docker ps
```

Debe existir:

```text
ticket-db
```

---

## Error: Kafka unavailable

Verificar:

```bash
docker compose ps
```

Kafka y Zookeeper deben estar healthy.

---

# Apagar todo al terminar

```bash
docker compose down
```