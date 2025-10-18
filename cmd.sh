 # !/bin/bash
 # Construir y levantar los contenedores en segundo plano
 docker compose up -d --build

# Construir y levantar solo uno de los componentes
 docker-compose up -d --build mcs-orders