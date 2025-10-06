package py.com.yensei.mcs.customers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import py.com.yensei.mcs.customers.models.CustomerModel;
import py.com.yensei.mcs.customers.services.CustomerService;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class ClientsController {

    @Value("${app.welcome-message:Default message2}")
    private String welcomeMessage;

    @Autowired
    private CustomerService service;

    @GetMapping("/message")
    public String getMessage() {
        return this.welcomeMessage;
    }

    @PostMapping("/customers")
    public CustomerModel postClient(@RequestBody CustomerModel customer) {
        return service.postCustomer(customer);

        
    }
}
