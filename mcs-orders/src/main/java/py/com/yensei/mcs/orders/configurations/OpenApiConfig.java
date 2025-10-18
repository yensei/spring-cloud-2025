package py.com.yensei.mcs.orders.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Orders Microservice API")
                        .version("v1.0.0")
                        .description("API for managing Orders.")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}