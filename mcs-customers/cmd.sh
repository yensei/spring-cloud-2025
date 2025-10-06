#!/bin/bash
# Construir el proyecto usando Maven
mvn clean package -DskipTests

# Contruir la imagen del servidor de configuración
docker build -t spring-cloud-customer-service:latest .

# Ejecutar el contenedor del servidor de configuración, agregar las variables de entorno para el usuario y token de GitHub
# Reemplaza 'tu-usuario' y 'tu-token' con tus credenciales reales
docker run -d --name mcs-customers -p 8080:8080 spring-cloud-customer-service:latest

# Deterner los contenedores y los remueve
docker-compose down

#Listar volumenes
docker volume ls
# borrar el volumen
docker volume rm spring-cloud_postgres_data