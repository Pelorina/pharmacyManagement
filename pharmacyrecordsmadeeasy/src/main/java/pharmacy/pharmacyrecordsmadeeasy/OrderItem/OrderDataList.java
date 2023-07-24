package pharmacy.pharmacyrecordsmadeeasy.OrderItem;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderDataList {
    private String medicineName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private Long orderNumber;
    private BigDecimal totalPrice;
}
