package pharmacy.pharmacyrecordsmadeeasy.Order;

import jakarta.persistence.*;
import lombok.*;
import pharmacy.pharmacyrecordsmadeeasy.OrderItem.ItemEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;
    private String medicineName;
    private String orderNumber;

}
