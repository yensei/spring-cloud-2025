package py.com.yensei.mcs.orders.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import py.com.yensei.mcs.orders.models.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;


@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)//no incluir campos null en la respuesta
public class OrderModel  {
    private Long id;

    @JsonProperty("customer_id")
    private Long customerId;
    @JsonProperty("customer_data")
    private CustomerModel customerData;    

    @Schema(description = "Status of the order. Possible values: PEN (Pending), CAN (Cancelled), AUT (Authorized), CON (Confirmed), DLT (Deleted)", 
            allowableValues = {"PEN", "CAN", "AUT", "CON", "DLT"})
    private OrderStatus status;
    @JsonProperty("total_amount")
    private BigDecimal totalAmount;
    @JsonProperty("order_date")
    private LocalDate orderDate;
    
}
