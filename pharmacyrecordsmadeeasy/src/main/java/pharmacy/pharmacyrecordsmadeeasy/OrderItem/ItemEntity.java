package pharmacy.pharmacyrecordsmadeeasy.OrderItem;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import pharmacy.pharmacyrecordsmadeeasy.Customer.CustomerEntity;
import pharmacy.pharmacyrecordsmadeeasy.Medicine.MedicineEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String medicineName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Long orderNumber;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private MedicineEntity medicine;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
}
