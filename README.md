# spring-cloud-2025
## CONFIG SERVER
Sirve para almacenar configuracion de cada componente 
### Actuators
* [/actuator/health](http://localhost:8888/actuator/health) *Ver el status del container*
* [/actuator/refresh](http://localhost:8888/actuator/refresh) *Resetea el cache*, cuando se cambia una configuracion en el remoto muchas veces queda cacheado, ahi hay que usar refresh para forzar el valor actualizado del remoto
### Configuraciones 
Las configuraciones deben estár en el repositorio remoto o local pero debe ser GIT, tomará precedencia este por sobre el application.yml del proyecto 
* MCS.orders => http://localhost:8888/mcs-orders/dev
* MCS.customers => http://localhost:8888/mcs-customers/dev

## Eureka Server 
* http://localhost:8761/

## MCS customers
Microservicio para la gestion de clientes. En 
[Swagger](http://localhost:8080/mcs-customers/swagger-ui/index.html) la documentación para su uso.


## MCS orders
Microservicio para la gestion de pedidos(compras). En 
[Swagger](http://localhost:8081/mcs-orders/swagger-ui/index.html) la documentación para su uso.


## Postgres 
Se crea una BD por componente pero actualmente están en el mismo container POSTGREs
### Como levantar una consola
`docker exec -it db-postgres sh`
### Utilizar PSQL
```bash
#Conectar
psql -U user -d orderdb #ó customerdb 
#Hacer un query
SELECT * FROM t_order;
#Ver la estructura de la tabla 
\d t_order
#Salir de PSQL
\q
#Salir de container
exit

```
