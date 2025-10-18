package py.com.yensei.mcs.orders.controllers.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import py.com.yensei.mcs.orders.controllers.OrderController;
import py.com.yensei.mcs.orders.models.OrderModel;
import py.com.yensei.mcs.orders.services.OrderService;

@Service
@Qualifier("orderControllerImpl")
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderModel> createOrder(OrderModel orderModel) {
        return new ResponseEntity<>(orderService.createOrder(orderModel), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Page<OrderModel>> getOrders(Long customerId, Pageable pageable) {
        return new ResponseEntity<>(orderService.getOrders(customerId, pageable), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderModel> getOrderById(Long id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderModel> updateOrder(Long id, OrderModel orderModel) {
        return new ResponseEntity<>(orderService.updateOrder(id, orderModel), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteOrder(Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
