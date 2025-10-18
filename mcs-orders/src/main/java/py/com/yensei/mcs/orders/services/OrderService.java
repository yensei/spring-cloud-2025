package py.com.yensei.mcs.orders.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import py.com.yensei.mcs.orders.clients.CustomerClient;
import py.com.yensei.mcs.orders.models.CustomerModel;
import py.com.yensei.mcs.orders.entities.OrderEntity;
import py.com.yensei.mcs.orders.exceptions.ResourceNotFoundException;
import py.com.yensei.mcs.orders.mappers.OrderMapper;
import py.com.yensei.mcs.orders.models.OrderModel;
import py.com.yensei.mcs.orders.repository.OrderRepository;

@Service
@RequiredArgsConstructor // genera constructor con parametros para los attr final, spring inyecta los beans automaticamente sin el autowired
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
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
        var orderEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        // Convertimos la entidad a modelo
        OrderModel orderModel = mapper.toModel(orderEntity);
        // Usamos el Feign Client para obtener los datos del cliente
        CustomerModel customerModel = customerClient.getCustomerById(orderEntity.getCustomerId());
        orderModel.setCustomerData(customerModel);
        return orderModel;
    }

    public OrderModel updateOrder(Long id, OrderModel order) {
        // 1. Verificar si la orden existe
        var existingEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        // 2. Mapear los datos del modelo de entrada a la entidad existente
        mapper.updateEntityFromModel(order, existingEntity);

        // 3. Guardar la entidad actualizada
        var updatedEntity = repository.save(existingEntity);

        // 4. Devolver el modelo actualizado
        return mapper.toModel(updatedEntity);
    }

    public void deleteOrder(Long id) {
        // Verificar si la orden existe antes de intentar borrarla
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
