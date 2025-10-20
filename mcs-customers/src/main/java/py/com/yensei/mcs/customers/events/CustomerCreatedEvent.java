package py.com.yensei.mcs.customers.events;
// Usamos un Record de Java para un DTO inmutable y conciso
public record CustomerCreatedEvent (
    Long customerId,
    String firstName,
    String email
) {}
