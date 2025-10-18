package py.com.yensei.mcs.orders.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import py.com.yensei.mcs.orders.entities.OrderEntity;

public interface OrderRepository  extends JpaRepository<OrderEntity, Long> {
    // Spring Data JPA se encarga de la implementación
    
    // Busca clientes cuyo nombre o apellido contenga el texto proporcionado, ignorando mayúsculas/minúsculas.
    Page<OrderEntity> findByCustomerId(Long customerId, Pageable pageable);

}
