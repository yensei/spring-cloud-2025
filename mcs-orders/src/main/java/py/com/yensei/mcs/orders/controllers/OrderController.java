package py.com.yensei.mcs.orders.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import py.com.yensei.mcs.orders.models.OrderModel;

public interface OrderController {
    
    ResponseEntity<OrderModel> createOrder(OrderModel orderModel);
    ResponseEntity<Page<OrderModel>> getOrders(Long customerId, Pageable pageable);
    ResponseEntity<OrderModel> getOrderById(Long id);
    ResponseEntity<OrderModel> updateOrder(Long id, OrderModel orderModel);
    ResponseEntity<Void> deleteOrder(Long id);

}