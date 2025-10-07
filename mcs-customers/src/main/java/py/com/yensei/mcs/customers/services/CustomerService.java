package py.com.yensei.mcs.customers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.yensei.mcs.customers.mappers.CustomerMapper;
import py.com.yensei.mcs.customers.models.CustomerModel;
import py.com.yensei.mcs.customers.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository repository;

    @Autowired
    CustomerMapper mapper;
    //aqui un comentario
    public CustomerModel createCustomer(CustomerModel customer) {
        var customerEntity = mapper.toEntity(customer);
        var savedEntity = repository.save(customerEntity);
        return mapper.toModel(savedEntity); 
    }

}
