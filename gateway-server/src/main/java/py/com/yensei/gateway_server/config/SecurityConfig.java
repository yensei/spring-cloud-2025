package py.com.yensei.gateway_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange(exchange -> exchange
                // Permite el acceso sin autenticación a la UI de Swagger de los microservicios
                .pathMatchers("/mcs-customers/swagger-ui/**", "/mcs-orders/swagger-ui/**", 
                              "/mcs-customers/v3/api-docs/**", "/mcs-orders/v3/api-docs/**").permitAll()
                // Cualquier otra petición requiere autenticación
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
