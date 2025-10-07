package py.com.yensei.mcs.customers.controllers.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import py.com.yensei.mcs.customers.controllers.ICustomerController;
import py.com.yensei.mcs.customers.models.CustomerModel;
import py.com.yensei.mcs.customers.services.CustomerService;

@Service
@Qualifier("customerControllerImpl")
public class CustomerControllerImpl implements ICustomerController {

    private final CustomerService customerService;

    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<CustomerModel> createCustomer(CustomerModel customerModel) {
        return new ResponseEntity<>(customerService.createCustomer(customerModel), HttpStatus.CREATED);
    }
}
