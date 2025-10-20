package py.com.yensei.mcs.notifications.events;

public record CustomerCreatedEvent(
    Long customerId,
    String firstName,
    String email
){}
