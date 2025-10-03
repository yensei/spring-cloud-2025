# Contruir la imagen del servidor de configuración
docker build -t spring-config-server:latest .

# Ejecutar el contenedor del servidor de configuración, agregar las variables de entorno para el usuario y token de GitHub
# Reemplaza 'tu-usuario' y 'tu-token' con tus credenciales reales
docker run -e GIT_USERNAME=tu-usuario -e GIT_PASSWORD=tu-token -d --name config-server -p 8888:8888 spring-config-server:latest
