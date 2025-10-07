package py.com.yensei.mcs.customers.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import py.com.yensei.mcs.customers.entities.CustomerEntity;
import py.com.yensei.mcs.customers.mappers.CustomerMapper;
import py.com.yensei.mcs.customers.models.CustomerModel;
import py.com.yensei.mcs.customers.repository.CustomerRepository;

@Service
@RequiredArgsConstructor // genera constructor con parametros para los attr final, spring inyecta los beans automaticamente sin el autowired
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    //aqui un comentario
    public CustomerModel createCustomer(CustomerModel customer) {
        var customerEntity = mapper.toEntity(customer);
        var savedEntity = repository.save(customerEntity);
        return mapper.toModel(savedEntity); 
    }

    public Page<CustomerModel> getCustomers(String firstname, String lastname, Pageable pageable) {
        Page<CustomerEntity> customerPage;
        if (firstname != null || lastname != null) {
            customerPage = repository.findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(firstname, lastname, pageable);
        } else {
            customerPage = repository.findAll(pageable);
        }
        return customerPage.map(mapper::toModel);
    }

}
