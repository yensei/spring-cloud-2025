package py.com.yensei.mcs.customers.controllers;

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
import py.com.yensei.mcs.customers.models.CustomerModel;

@RestController
@RequestMapping("/customers")
@Qualifier("customerControllerDecorator")
@Tag(name = "Customer Management", description = "API for creating, retrieving, and managing customers.")
public class CustomerControllerDecorator implements ICustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerDecorator.class);

    private final ICustomerController customerController;
    public CustomerControllerDecorator(@Qualifier("customerControllerImpl") ICustomerController customerController) {
        this.customerController = customerController;
    }

    @Override
    @PostMapping
    @Operation(summary = "Create a new customer", description = "Creates a new customer record in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully", 
                         content = { @Content(mediaType = "application/json", 
                         schema = @Schema(implementation = CustomerModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data", 
                         content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", 
                         content = @Content)
    })
    public ResponseEntity<CustomerModel> createCustomer(@RequestBody CustomerModel customerModel) {
        logger.debug(">>> Decorator: Interceptando petición para crear cliente: {}", customerModel);
        return this.customerController.createCustomer(customerModel);
    }

    @Override
    @GetMapping
    @Operation(
        summary = "Get a paginated list of customers", 
        description = "Retrieves customers with optional filtering by first name or last name. " +
                      "Pagination is supported via `page`, `size`, and `sort` parameters. " +
                      "The `sort` parameter format is `property,direction` (e.g., `lastname,asc`). " +
                      "Multiple sort criteria can be provided."
    )
    public ResponseEntity<Page<CustomerModel>> getCustomers(
            @RequestParam(name = "firstname", required = false) String firstname,
            @RequestParam(name = "lastname", required = false) String lastname,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        logger.debug(">>> Decorator: Interceptando petición para obtener clientes con filtro: firstname={}, lastname={}, pageable={}", firstname, lastname, pageable);
        return this.customerController.getCustomers(firstname, lastname, pageable);
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Get a customer by ID", description = "Retrieves a single customer record by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                         content = { @Content(mediaType = "application/json",
                         schema = @Schema(implementation = CustomerModel.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                         content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                         content = @Content)
    })
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable Long id) {
        logger.debug(">>> Decorator: Interceptando petición para obtener cliente por ID: {}", id);
        return this.customerController.getCustomerById(id);
    }

    @Override
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing customer", description = "Updates a customer's details by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                         content = { @Content(mediaType = "application/json",
                         schema = @Schema(implementation = CustomerModel.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                         content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                         content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                         content = @Content)
    })
    public ResponseEntity<CustomerModel> updateCustomer(@PathVariable Long id, @RequestBody CustomerModel customerModel) {
        logger.debug(">>> Decorator: Interceptando petición para actualizar cliente por ID: {}", id);
        return this.customerController.updateCustomer(id, customerModel);
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer", description = "Deletes a customer record by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully",
                         content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                         content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                         content = @Content)
    })
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        logger.debug(">>> Decorator: Interceptando petición para eliminar cliente por ID: {}", id);
        return this.customerController.deleteCustomer(id);
    }

}
