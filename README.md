# spring-cloud-2025
## CONFIG SERVER
Sirve para almacenar configuracion de cada componente 
### Actuators
http://localhost:8888/actuator/health *Ver el status del container*
http://localhost:8888/actuator/refresh *Resetea el cache*, cuando se cambia una configuracion en el remoto muchas veces queda cacheado, ahi hay que usar refresh para forzar el valor actualizado del remoto
