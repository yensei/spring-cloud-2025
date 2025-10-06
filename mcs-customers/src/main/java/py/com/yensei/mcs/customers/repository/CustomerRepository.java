package py.com.yensei.mcs.customers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.com.yensei.mcs.customers.entities.CustomerEntity;

public interface CustomerRepository  extends JpaRepository<CustomerEntity, Long> {
    // Spring Data JPA se encarga de la implementación


}
