package py.com.yensei.mcs.orders.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import py.com.yensei.mcs.orders.models.OrderModel;

@RestController
@RequestMapping("/orders")
@Qualifier("orderControllerDecorator")
@Tag(name = "Order Management", description = "API for creating, retrieving, and managing mcs-orders.")
public class OrderControllerDecorator implements OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderControllerDecorator.class);

    private final OrderController orderController;
    public OrderControllerDecorator(@Qualifier("orderControllerImpl") OrderController orderController) {
        this.orderController = orderController;
    }

    @Override
    @PostMapping
    @Operation(summary = "Create a new order", description = "Creates a new order record in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully", 
                         content = { @Content(mediaType = "application/json", 
                         schema = @Schema(implementation = OrderModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                         content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", 
                         content = @Content)
    })
    public ResponseEntity<OrderModel> createOrder(@RequestBody OrderModel orderModel) {
        logger.debug(">>> Decorator: Interceptando petición para crear Order: {}", orderModel);
        return this.orderController.createOrder(orderModel);
    }

    @Override
    @GetMapping
    @Operation(
        summary = "Get a paginated list of mcs-orders", 
        description = "Retrieves mcs-orders with optional filtering by first name or last name. " +
                      "Pagination is supported via `page`, `size`, and `sort` parameters. " +
                      "The `sort` parameter format is `property,direction` (e.g., `lastname,asc`). " +
                      "Multiple sort criteria can be provided."
    )
    public ResponseEntity<Page<OrderModel>> getOrders(
            @RequestParam(name = "customerId", required = false) Long customerId,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        logger.debug(">>> Decorator: Interceptando petición para obtener Orders con filtro: customerId={}, pageable={}", customerId, pageable);
        return this.orderController.getOrders(customerId, pageable);
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Get a order by ID", description = "Retrieves a single order record by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found",
                         content = { @Content(mediaType = "application/json",
                         schema = @Schema(implementation = OrderModel.class)) }),
            @ApiResponse(responseCode = "404", description = "Order not found",
                         content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                         content = @Content)
    })
    public ResponseEntity<OrderModel> getOrderById(@PathVariable Long id) {
        logger.debug(">>> Decorator: Interceptando petición para obtener Order por ID: {}", id);
        return this.orderController.getOrderById(id);
    }

    @Override
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing order", description = "Updates a order's details by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully",
                         content = { @Content(mediaType = "application/json",
                         schema = @Schema(implementation = OrderModel.class)) }),
            @ApiResponse(responseCode = "404", description = "Order not found",
                         content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                         content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                         content = @Content)
    })
    public ResponseEntity<OrderModel> updateOrder(@PathVariable Long id, @RequestBody OrderModel orderModel) {
        logger.debug(">>> Decorator: Interceptando petición para actualizar Order por ID: {}", id);
        return this.orderController.updateOrder(id, orderModel);
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a order", description = "Deletes a order record by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted successfully",
                         content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                         content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                         content = @Content)
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        logger.debug(">>> Decorator: Interceptando petición para eliminar Order por ID: {}", id);
        return this.orderController.deleteOrder(id);
    }

}
