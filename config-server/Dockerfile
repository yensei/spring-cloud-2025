# 1. Etapa de Construcción (Build Stage) - Usa Java para compilar
# Es una práctica recomendada usar una imagen base ligera para la ejecución final.
FROM eclipse-temurin:17-jdk-jammy AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el POM primero para permitir que Docker cachee las dependencias
# El archivo 'mvnw' es el wrapper de Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Descargar las dependencias. Si el pom.xml no cambia, esta etapa se cachea.
RUN ./mvnw dependency:go-offline -B

# Copiar el resto del código fuente
COPY src src

# Compilar el proyecto y generar el JAR ejecutable (fat JAR)
# El flag -DskipTests es para acelerar el proceso de construcción.
RUN ./mvnw package -DskipTests

# Localizar el nombre exacto del JAR (config-server-1.0.0-SNAPSHOT.jar)
ARG JAR_FILE=target/config-server-1.0.0-SNAPSHOT.jar
# Renombrar a un nombre fijo (opcional, pero ayuda)
RUN cp ${JAR_FILE} app.jar

# 2. ETAPA DE EJECUCIÓN: Usa una imagen ligera (solo JRE) para el runtime final.
FROM eclipse-temurin:17-jre-jammy


# Expone el puerto por el que correrá la app (8888 por defecto)
EXPOSE 8888

# Copia el JAR generado de la etapa de construcción o de tu disco local
# Si lo copiaste antes, solo renombra y copia:
COPY --from=build /app/app.jar app.jar

# Define el comando de inicio.
# -Djava.security.egd=file:/dev/./urandom es para optimizar la generación de números aleatorios (mejora el rendimiento de Spring Boot en contenedores).
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]