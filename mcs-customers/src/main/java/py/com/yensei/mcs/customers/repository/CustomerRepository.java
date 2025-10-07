package py.com.yensei.mcs.customers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import py.com.yensei.mcs.customers.entities.CustomerEntity;

public interface CustomerRepository  extends JpaRepository<CustomerEntity, Long> {
    // Spring Data JPA se encarga de la implementación
    
    // Busca clientes cuyo nombre o apellido contenga el texto proporcionado, ignorando mayúsculas/minúsculas.
    Page<CustomerEntity> findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(String firstname, String lastname, Pageable pageable);

}
