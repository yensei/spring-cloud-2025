package py.com.yensei.mcs.orders.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import py.com.yensei.mcs.orders.entities.OrderEntity;
import py.com.yensei.mcs.orders.mappers.OrderMapper;
import py.com.yensei.mcs.orders.models.OrderModel;
import py.com.yensei.mcs.orders.repository.OrderRepository;

@Service
@RequiredArgsConstructor // genera constructor con parametros para los attr final, spring inyecta los beans automaticamente sin el autowired
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    //aqui un comentario
    public OrderModel createOrder(OrderModel order) {
        var orderEntity = mapper.toEntity(order);
        var savedEntity = repository.save(orderEntity);
        return mapper.toModel(savedEntity); 
    }

    public Page<OrderModel> getOrders(Long customerId, Pageable pageable) {
        Page<OrderEntity> orderPage;
        if (customerId != null) {
            orderPage = repository.findByCustomerId(customerId, pageable);
        } else {
            orderPage = repository.findAll(pageable);
        }
        return orderPage.map(mapper::toModel);
    }

    public OrderModel getOrderById(Long id) {
        // Busca la entidad por ID. Si no la encuentra, lanza una excepción.
        // En un futuro, se puede crear una excepción personalizada (ej. ResourceNotFoundException).
        var orderEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return mapper.toModel(orderEntity);
    }

    public OrderModel updateOrder(Long id, OrderModel order) {
        // 1. Verificar si el cliente existe
        var existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // 2. Mapear los datos del modelo de entrada a la entidad existente
        mapper.updateEntityFromModel(order, existingEntity);

        // 3. Guardar la entidad actualizada
        var updatedEntity = repository.save(existingEntity);

        // 4. Devolver el modelo actualizado
        return mapper.toModel(updatedEntity);
    }

    public void deleteOrder(Long id) {
        // Verificar si el cliente existe antes de intentar borrarlo
        if (!repository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
