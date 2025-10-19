# 1. Etapa de Construcción (Build Stage)
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

# Copiar el POM primero para cachear las dependencias
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# ¡CRÍTICO! Copia el settings.xml para autenticación con el repositorio privado de Maven
COPY ./mvn-settings.xml /root/.m2/settings.xml

# Descargar dependencias
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente y construir el JAR
COPY src src
RUN ./mvnw package -DskipTests

# 2. Etapa de Ejecución (Runtime Stage)
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

