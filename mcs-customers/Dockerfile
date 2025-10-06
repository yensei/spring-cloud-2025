# 1. Etapa de Construcción (Build Stage) - Usa Java para compilar
# Es una práctica recomendada usar una imagen base ligera para la ejecución final.
FROM eclipse-temurin:17-jdk-jammy AS builder

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el POM primero para permitir que Docker cachee las dependencias
# El archivo 'mvnw' es el wrapper de Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

COPY mvn-settings.xml /root/.m2/settings.xml

# Descargar las dependencias. Si el pom.xml no cambia, esta etapa se cachea.
RUN ./mvnw dependency:go-offline -B

# Copiar el resto del código fuente
COPY src src

# Compilar el proyecto y generar el JAR ejecutable (fat JAR)
# El flag -DskipTests es para acelerar el proceso de construcción.
RUN ./mvnw package -DskipTests

# Localizar el nombre exacto del JAR (config-server-1.0.0-SNAPSHOT.jar)
#ARG JAR_FILE=target/customers-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=target/*.jar


# Renombrar a un nombre fijo (opcional, pero ayuda)
RUN cp ${JAR_FILE} app.jar

# Crea un usuario no-root por seguridad
RUN groupadd spring && useradd -s /bin/false -g spring spring
USER spring:spring

# Expone el puerto por el que correrá la app (8888 por defecto)
EXPOSE 8080

# Copia el JAR generado de la etapa de construcción o de tu disco local
#COPY --from=builder /app/app.jar app.jar
#COPY --from=builder /app/target/customers-0.0.1-SNAPSHOT.jar app.jar

# Define el comando de inicio.
# -Djava.security.egd=file:/dev/./urandom es para optimizar la generación de números aleatorios (mejora el rendimiento de Spring Boot en contenedores).
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
ENTRYPOINT ["java","-jar","app.jar"]