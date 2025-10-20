# ruta: mcs-notifications/Dockerfile
# Etapa 1: Construcción con Maven
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Copia los archivos de Maven y el POM
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# ¡CRÍTICO! Copia el settings.xml para autenticación con el repositorio privado de Maven
COPY ./mvn-settings.xml /root/.m2/settings.xml

# Descargar las dependencias. Si el pom.xml no cambia, esta etapa se cachea.
RUN ./mvnw dependency:go-offline -B

COPY src src

# Compilar el proyecto y generar el JAR ejecutable
RUN ./mvnw package -DskipTests

# --- Etapa 2: Ejecución (Runtime) ---
# Usa una imagen ligera solo con JRE para ejecutar la aplicación
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia el JAR construido desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
