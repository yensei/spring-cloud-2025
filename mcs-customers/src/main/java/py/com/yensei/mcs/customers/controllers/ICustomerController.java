package py.com.yensei.mcs.customers.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import py.com.yensei.mcs.customers.models.CustomerModel;

public interface ICustomerController {
    
    ResponseEntity<CustomerModel> createCustomer(CustomerModel customerModel);
    ResponseEntity<Page<CustomerModel>> getCustomers(String firstname, String lastname, Pageable pageable);

}