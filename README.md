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

## Monitoreo
### Prometheus
Prometheus es el corazón del monitoreo de métricas. Su trabajo es "raspar" (hacer scrape) periódicamente los endpoints /actuator/prometheus que tus microservicios ahora exponen
http://localhost:9090

http://localhost:9090/targets 

#### Ajustar configuraciones de prometheus
```bash
#Permite tomar la nueva configuracion y no volver a despegar todos los containers
docker compose up -d --force-recreate prometheus
```

### Grafana
Grafana es tu centro de visualización. Usará los datos de Prometheus (métricas) y Loki (logs) para crear gráficos y dashboards.

* Abre Grafana: Ve a http://localhost:3000.
* Inicia sesión: El usuario es admin y la contraseña es admin (la configuramos en el docker-compose.yml). 
* Verifica las "Data Sources":
* En el menú de la izquierda, ve al ícono de la tuerca (⚙️ Administration).
Haz clic en Data sources. Deberías ver Prometheus y Loki listados ya que grafana se autoconfiguró para conectarse a ellos

#### La vista "Explore" de Grafana es ideal para jugar con los datos.

* Abre "Explore": En el menú de la izquierda, haz clic en el ícono de la brújula (🧭 Explore).
* Selecciona Prometheus: En la parte superior izquierda, asegúrate de que el desplegable muestre Prometheus.
* Construye una consulta:
* Haz clic en el botón Metrics browser.
** Escribe jvm_memory_used_bytes y selecciónala. Esta métrica muestra el uso de memoria de la JVM para cada servicio.
* Haz clic en Run query en la esquina superior derecha.
** ¡Verás un gráfico en tiempo real del uso de memoria de tus aplicaciones! Puedes filtrar por job o instance para ver un servicio específico.
#### Importar tu Primer Dashboard
La verdadera potencia de Grafana está en los dashboards. Vamos a importar uno muy popular para aplicaciones Java.

* Ve a la sección de Dashboards: En el menú de la izquierda, haz clic en el ícono de los cuatro cuadrados (⊞ Dashboards).
* Inicia la importación: En la esquina superior derecha, haz clic en New -> Import.
* Importar desde Grafana.com: En el campo "Import via grafana.com", pega el ID: 4701 y haz clic en Load.
* Configura el Dashboard:
* En la parte inferior, te pedirá seleccionar la fuente de datos de Prometheus. Elige Prometheus en el desplegable.
* Haz clic en Import.
