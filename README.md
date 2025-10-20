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

## Autenticacion
### Keykloack
Paso 0: Crear el Realm
Al iniciar Keycloak por primera vez, solo existe el realm `master`. Debemos crear uno específico para nuestra aplicación.

1.  **Accede a la Consola de Administración:** Ve a `http://localhost:9990` (user: `admin`, pass: `admin`).
2.  **Crea el Realm:**
    *   En la esquina superior izquierda, haz clic sobre el nombre del realm actual (`master`).
    *   Haz clic en el botón **Create Realm**.
    *   En **Realm name**, escribe `spring-cloud-realm`.
    *   Haz clic en **Create**.

Keycloak te cambiará automáticamente al nuevo realm. Todos los pasos siguientes se realizan dentro de `spring-cloud-realm`.

Paso 1: Configurar un Cliente y Usuario en Keycloak
Primero, necesitamos decirle a Keycloak que existirá una "aplicación cliente" (en este caso, Postman) que solicitará tokens, y crear un usuario que pueda iniciar sesión.

Accede a la Consola de Keycloak:

Abre tu navegador y ve a http://localhost:9990.
Inicia sesión en la consola de administración (usuario: admin, contraseña: admin).
Selecciona tu Realm:

En la esquina superior izquierda, asegúrate de que el realm seleccionado sea spring-cloud-realm.
Crea un Cliente para Postman:

En el menú de la izquierda, ve a Clients y haz clic en Create client.
Client ID: postman-client (o el nombre que prefieras).
Deja el resto como está y haz clic en Next.
Activa la opción Client authentication y Direct access grants. Esto es crucial para permitir el flujo de grant_type=password que usaremos.
Haz clic en Save.
Crea un Usuario de Prueba:

En el menú de la izquierda, ve a Users y haz clic en Add user.
Username: testuser.
Haz clic en Create.
Ahora, ve a la pestaña Credentials del usuario que acabas de crear.
Haz clic en Set password.
Password: password (o la que quieras).
**MUY IMPORTANTE:** Desactiva la opción **Temporary**. Si se queda activada, recibirás el error "Account is not fully set up".
Haz clic en Save.
¡Listo! Ya tienes un cliente (postman-client) y un usuario (testuser) listos para autenticarse.

Paso 2: Obtener el Token JWT con Postman
Ahora vamos a pedirle a Keycloak que nos dé un token para testuser.

Crea una nueva petición en Postman:

Método: POST
URL: http://localhost:9990/realms/spring-cloud-realm/protocol/openid-connect/token
Configura el Body:

Selecciona la pestaña Body.
Elige el tipo x-www-form-urlencoded.
Añade las siguientes claves (keys) y valores (values).

*   `grant_type`: `password`
*   `client_id`: `postman-client` (o el nombre de tu cliente)
*   `username`: `testuser`
*   `password`: `password`
*   `client_secret`: (Añade esto **solo si tu cliente es confidencial**). Para obtenerlo, ve a la pestaña `Credentials` de tu cliente en Keycloak.

Tu configuración debería verse así:

Envía la petición:

Haz clic en Send.
Si todo está correcto, Keycloak te devolverá una respuesta JSON con varios tokens. El que nos interesa es el access_token.
json
 Show full code block 
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIg... (un token muy largo) ...",
    "expires_in": 300,
    "refresh_expires_in": 1800,
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCIg...",
    "token_type": "Bearer",
    "not-before-policy": 0,
    "session_state": "...",
    "scope": "email profile"
}
Paso 3: Usar el Token para Acceder a un Endpoint Protegido
Ahora que tienes el access_token, puedes usarlo para autenticarte en tus microservicios a través del Gateway.

Copia el access_token: Selecciona y copia el valor completo del access_token de la respuesta anterior.

Crea una nueva petición para tu API:

Método: GET (o el que quieras probar)
URL: http://localhost:8090/api/customers (recuerda usar el puerto del Gateway y la ruta /api/...)
Configura la Autorización:

Ve a la pestaña Authorization.
En el desplegable Type, selecciona Bearer Token.
En el campo Token de la derecha, pega el access_token que copiaste.
Envía la petición:

Haz clic en Send.
¡Y voilà! Esta vez, en lugar de un 401 Unauthorized, deberías recibir una respuesta 200 OK con los datos de tu microservicio. El Gateway ha validado el token, ha visto que es correcto y ha permitido que la petición continúe hasta mcs-customers.

Alternativa con curl (para la terminal)
Si prefieres la línea de comandos, estos son los comandos equivalentes:

1. Obtener el Token:

```bash
 #Show full code block 
curl --location 'http://localhost:9990/realms/spring-cloud-realm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=postman-client' \
--data-urlencode 'username=testuser' \
--data-urlencode 'password=password' \
--data-urlencode 'grant_type=password'
```
2. Usar el Token: (Reemplaza TU_TOKEN_AQUI con el access_token obtenido)
```bash
curl --location 'http://localhost:8090/api/customers' \
--header 'Authorization: Bearer TU_TOKEN_AQUI'
```
Con esto ya tienes el flujo completo de autenticación y autorización funcionando. 

## KAFKA
### Validar si hay mensajes
Par verificar que el productor está enviando los mensajes correctamente, incluso antes de tener un consumidor, es un paso fundamental para depurar y tener confianza en tu arquitectura.

La forma más directa de "espiar" lo que está pasando dentro de Kafka es usar las herramientas de línea de comandos que vienen incluidas en la propia imagen de Docker de Kafka.

#### Paso a paso:

Verificación con la Herramienta de Consola de Kafka
Vamos a abrir una terminal dentro de tu contenedor de Kafka y nos suscribiremos al topic customers.v1.events para ver los mensajes en tiempo real a medida que llegan.

* Paso 1: Asegúrate de que todo esté en ejecución

Primero, levanta toda tu infraestructura como de costumbre:

```bash
docker compose up -d --build
```
* Paso 2: Abre una terminal dentro del contenedor de Kafka

Abre una nueva terminal en tu máquina y ejecuta el siguiente comando. Esto te dará acceso a una sesión de bash dentro del contenedor kafka:

```bash
docker exec -it kafka bash
```
Verás que tu prompt cambia, indicando que ahora estás dentro del contenedor.

* Paso 3: Inicia el consumidor de consola

Una vez dentro del contenedor, ejecuta este comando. Este programa se conectará al broker de Kafka y escuchará los mensajes del topic que le indiques:

```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic customers.v1.events --from-beginning
```

Desglosemos el comando:

kafka-console-consumer: Es el nombre de la herramienta.
--bootstrap-server localhost:9092: Le dice dónde está el broker de Kafka. Como estamos dentro del contenedor kafka, podemos usar localhost.
--topic customers.v1.events: Este es el punto clave. Es el nombre exacto del topic que definiste en el application.yml de mcs-customers.
--from-beginning: Es muy útil. Le dice al consumidor que lea todos los mensajes que ya existan en el topic desde el principio, no solo los nuevos.
Después de ejecutar el comando, la terminal se quedará "esperando", con un cursor parpadeando. ¡Eso es bueno! Significa que está escuchando activamente.

* Paso 4: Envía un evento creando un nuevo cliente

Ahora, con la terminal del consumidor de Kafka abierta y esperando, usa Postman o curl para crear un nuevo cliente a través de tu API Gateway, como lo harías normalmente.

Ejemplo con curl (necesitarás un token de acceso válido):

```bash
# Show full code block 
# Reemplaza TU_TOKEN_AQUI con un token válido de Keycloak
curl --location 'http://localhost:8090/api/customers' \
--header 'Authorization: Bearer TU_TOKEN_AQUI' \
--header 'Content-Type: application/json' \
--data '{
    "firstname": "John",
    "lastname": "Doe",
    "email": "john.doe@example.com"
}'
```
* Paso 5: ¡Observa la Magia!

En el instante en que mcs-customers procese la petición, verás aparecer el evento en la terminal donde tienes el consumidor de consola. El resultado será algo así:

```json
{"customerId":1,"firstName":"John","email":"john.doe@example.com"}
```
¡Listo! Esto es la prueba irrefutable de que:

Tu mcs-customers se conectó correctamente a Kafka.
StreamBridge funcionó y serializó tu objeto CustomerCreatedEvent a JSON.
El mensaje fue publicado exitosamente en el topic customers.v1.events.