# spring-cloud-2025
## CONFIG SERVER
Sirve para almacenar configuracion de cada componente 
### Actuators
* [/actuator/health](http://localhost:8888/actuator/health) *Ver el status del container*
* [/actuator/refresh](http://localhost:8888/actuator/refresh) *Resetea el cache*, cuando se cambia una configuracion en el remoto muchas veces queda cacheado, ahi hay que usar refresh para forzar el valor actualizado del remoto

## MCS customers
Microservicio para la gestion de clientes. En 
[Swagger](http://localhost:8080/mcs-customers/swagger-ui/index.html) la documentación para su uso.


## MCS orders
Microservicio para la gestion de pedidos(compras). En 
[Swagger](http://localhost:8081/mcs-orders/swagger-ui/index.html) la documentación para su uso.



