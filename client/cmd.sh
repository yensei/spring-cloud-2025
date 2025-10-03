# compilar con maven
mvn clean package

# Para desplegar (profiles : dev, hml y prd)
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"