# --- Etapa 1: Construcción (Builder) ---
# Usa una imagen con JDK para compilar la aplicación
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app

# Copia los archivos de Maven y el POM
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# ¡CRÍTICO! Copia el settings.xml para autenticación con el repositorio privado de Maven
COPY ./mvn-settings.xml /root/.m2/settings.xml

# Descargar las dependencias. Si el pom.xml no cambia, esta etapa se cachea.
RUN ./mvnw dependency:go-offline -B

# Copiar el resto del código fuente
COPY src src

# Compilar el proyecto y generar el JAR ejecutable
RUN ./mvnw package -DskipTests

# Localizar el nombre exacto del JAR (config-server-1.0.0-SNAPSHOT.jar)
#ARG JAR_FILE=target/customers-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=target/*.jar
# --- Etapa 2: Ejecución (Runtime) ---
# Usa una imagen ligera solo con JRE para ejecutar la aplicación
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia el JAR generado desde la etapa 'builder'
COPY --from=builder /app/target/*.jar app.jar

# Expone el puerto que la aplicación usará dentro del contenedor (buena práctica/documentación)
EXPOSE 8080

# Define el comando de inicio.
ENTRYPOINT ["java", "-jar", "app.jar"]