# --------------------------------------------------------------------------------------------------
# ETAPA 1: BUILDER - Para compilar el código y generar el JAR ejecutable
# --------------------------------------------------------------------------------------------------
# Utilizamos una imagen con JDK y herramientas de construcción (Maven o Gradle)
FROM eclipse-temurin:17-jdk-jammy AS builder

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Maven (pom.xml) y Gradle (build.gradle)
# Esto permite que Docker cachee las dependencias si el pom.xml no cambia
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

# ¡CRÍTICO! Copia el settings.xml para autenticación con el repositorio privado de Maven
COPY ./mvn-settings.xml /root/.m2/settings.xml

# Descargar las dependencias. Si el pom.xml no cambia, esta etapa se cachea.
RUN ./mvnw dependency:go-offline -B

# Copia el código fuente de la aplicación
COPY src ./src

# Ejecuta la construcción de Spring Boot. 
# La opción '-DskipTests' es común para acelerar el build en Docker
RUN ./mvnw clean package -DskipTests

# --------------------------------------------------------------------------------------------------
# ETAPA 2: RUNTIME - Para ejecutar el JAR final
# --------------------------------------------------------------------------------------------------
# Usamos una imagen base más ligera (solo JRE) para el runtime final
FROM eclipse-temurin:17-jre-jammy

# Define un argumento para el nombre del archivo JAR generado
ARG JAR_FILE=target/*.jar

# Copia el JAR generado de la ETAPA 1 a la ETAPA 2
COPY --from=builder /app/${JAR_FILE} app.jar

# Expone el puerto por defecto de Spring Boot
EXPOSE 8761

# Comando para ejecutar la aplicación
# Usar 'exec' garantiza que la aplicación sea el proceso principal (PID 1), 
# lo cual es crucial para recibir señales de Kubernetes
ENTRYPOINT ["java","-jar","/app.jar"]