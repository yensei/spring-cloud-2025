// ruta: mcs-notifications/src/main/java/py/com/yensei/mcs/notifications/listeners/CustomerEventListener.java
package py.com.yensei.mcs.notifications.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import py.com.yensei.mcs.notifications.events.CustomerCreatedEvent;
import java.util.function.Consumer;

@Configuration
public class CustomerEventListener {

    private static final Logger log = LoggerFactory.getLogger(CustomerEventListener.class);

    @Bean
    public Consumer<CustomerCreatedEvent> processCustomerCreatedEvent() {
        return event -> {
            log.info("==================================================");
            log.info("🎉 ¡Evento de Cliente Nuevo Recibido! ID: {}", event.customerId());
            log.info("Simulando envío de email de bienvenida a: {}", event.email());
            log.info("==================================================");

            // Aquí, en un futuro, podrías llamar a un servicio de email
            // como Amazon SES, SendGrid, etc.
        };
    }
}
