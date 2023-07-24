package pharmacy.pharmacyrecordsmadeeasy.Medicine;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import pharmacy.pharmacyrecordsmadeeasy.OrderItem.ItemEntity;

import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String dosage;
    private Integer quantity;
    private String price;
    private Date expiryDate;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    @OneToMany(mappedBy = "medicine")
    private Set<ItemEntity> itemEntities;
}
