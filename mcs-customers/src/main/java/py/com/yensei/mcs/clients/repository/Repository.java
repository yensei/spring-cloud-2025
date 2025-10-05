package py.com.yensei.mcs.clients.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.com.yensei.mcs.clients.entities.Customer;

public interface Repository  extends JpaRepository<Customer, Long> {
    // Spring Data JPA se encarga de la implementación
}
