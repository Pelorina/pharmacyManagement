package pharmacy.pharmacyrecordsmadeeasy.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pharmacy.pharmacyrecordsmadeeasy.Order.OrderEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequest {
    private String medicineName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Date orderDate;
    private Long orderNumber;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<OrderEntity> order;


}
