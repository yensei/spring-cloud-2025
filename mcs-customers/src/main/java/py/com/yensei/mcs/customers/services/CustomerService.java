package py.com.yensei.mcs.customers.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import py.com.yensei.mcs.customers.entities.CustomerEntity;
import py.com.yensei.mcs.customers.events.CustomerCreatedEvent;
import py.com.yensei.mcs.customers.mappers.CustomerMapper;
import py.com.yensei.mcs.customers.models.CustomerModel;
import py.com.yensei.mcs.customers.repository.CustomerRepository;

@Service
@RequiredArgsConstructor // genera constructor con parametros para los attr final, spring inyecta los beans automaticamente sin el autowired
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    private final StreamBridge streamBridge;
    //aqui un comentario
    public CustomerModel createCustomer(CustomerModel customer) {
        var customerEntity = mapper.toEntity(customer);
        var savedEntity = repository.save(customerEntity);

        CustomerCreatedEvent event = new CustomerCreatedEvent(
            savedEntity.getId(),
            savedEntity.getFirstname(),
            savedEntity.getEmail()
        );

        streamBridge.send("sendCustomerCreatedEvent-out-0", event);
        log.debug(">>> Evento CustomerCreatedEvent enviado para el cliente ID: {}", savedEntity.getId());


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

    public CustomerModel getCustomerById(Long id) {
        // Busca la entidad por ID. Si no la encuentra, lanza una excepción.
        // En un futuro, se puede crear una excepción personalizada (ej. ResourceNotFoundException).
        var customerEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return mapper.toModel(customerEntity);
    }

    public CustomerModel updateCustomer(Long id, CustomerModel customer) {
        // 1. Verificar si el cliente existe
        var existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        // 2. Mapear los datos del modelo de entrada a la entidad existente
        mapper.updateEntityFromModel(customer, existingEntity);

        // 3. Guardar la entidad actualizada
        var updatedEntity = repository.save(existingEntity);

        // 4. Devolver el modelo actualizado
        return mapper.toModel(updatedEntity);
    }

    public void deleteCustomer(Long id) {
        // Verificar si el cliente existe antes de intentar borrarlo
        if (!repository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
