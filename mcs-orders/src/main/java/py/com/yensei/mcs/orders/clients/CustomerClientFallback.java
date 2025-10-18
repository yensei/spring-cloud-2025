package py.com.yensei.mcs.orders.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import py.com.yensei.mcs.orders.models.CustomerModel;

@Component
public class CustomerClientFallback implements CustomerClient {

    private static final Logger logger = LoggerFactory.getLogger(CustomerClientFallback.class);


    @Override
    public CustomerModel getCustomerById(Long id) {
        logger.warn("Fallback for getCustomerById. Customer service is down or timed out for customerId: {}", id);
        // Devolvemos un objeto por defecto para evitar NullPointerExceptions y señalar que los datos del cliente no están disponibles.
        return new CustomerModel("N/A", "Service Unavailable", null,null,null,null);
    }

}
