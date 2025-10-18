package py.com.yensei.mcs.orders.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import py.com.yensei.mcs.orders.models.enums.OrderStatus;

@Entity
@Table(name = "t_order")
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_id")
    private Long customerId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "order_date")
    private LocalDate orderDate;
}
