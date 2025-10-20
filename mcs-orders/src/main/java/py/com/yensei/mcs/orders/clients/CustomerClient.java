package py.com.yensei.mcs.orders.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import py.com.yensei.mcs.orders.models.CustomerModel;

// El 'name' debe coincidir con el spring.application.name del microservicio de clientes
// El 'path' debe coincidir con el server.servlet.context-path del otro microservicio
@FeignClient(name = "mcs-customers", fallback = CustomerClientFallback.class)
public interface CustomerClient {

    @GetMapping("/customers/{id}")
    CustomerModel getCustomerById(@PathVariable("id") Long id);
}
