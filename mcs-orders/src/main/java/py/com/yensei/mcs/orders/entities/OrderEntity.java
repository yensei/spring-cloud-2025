package py.com.yensei.mcs.orders.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order")
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private String status;
    private BigDecimal totalAmount;
    @Column(name = "order_date")
    private LocalDate orderDate;
}
