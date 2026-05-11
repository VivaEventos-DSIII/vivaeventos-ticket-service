FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Descargar dependencias antes de copiar fuentes (cache layer)
COPY pom.xml ./
RUN mvn dependency:go-offline -q

# Compilar
COPY src ./src
RUN mvn package -DskipTests -q

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S vivaeventos && adduser -S vivaeventos -G vivaeventos
USER vivaeventos
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
