# Contruir la imagen del servidor de configuración
docker build -t spring-cloud-customer-service:latest .

# Ejecutar el contenedor del servidor de configuración, agregar las variables de entorno para el usuario y token de GitHub
# Reemplaza 'tu-usuario' y 'tu-token' con tus credenciales reales
docker run -d --name mcs-customers -p 8080:8080 spring-cloud-customer-service:latest
