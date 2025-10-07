package py.com.yensei.mcs.customers.controllers;

import org.springframework.http.ResponseEntity;

import py.com.yensei.mcs.customers.models.CustomerModel;

public interface ICustomerController {
    
    ResponseEntity<CustomerModel> createCustomer(CustomerModel customerModel);

}