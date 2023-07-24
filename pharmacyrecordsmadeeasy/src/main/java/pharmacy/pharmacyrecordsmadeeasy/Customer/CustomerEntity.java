package pharmacy.pharmacyrecordsmadeeasy.Customer;

import jakarta.persistence.*;
import lombok.*;
import pharmacy.pharmacyrecordsmadeeasy.OrderItem.ItemEntity;
import pharmacy.pharmacyrecordsmadeeasy.Response.Roles;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    @Column(name = "contact_information")
    private String contactInformation;
    private String username;
    private String gender;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Roles roles;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemEntity> itemEntities;

    public void addOrderItem(ItemEntity orderItem) {
        if (orderItem != null) {
            if (itemEntities == null) {
                itemEntities = new HashSet<>();
            }
            orderItem.setCustomer(this);
            itemEntities.add(orderItem);
        }
    }

    public void removeOrderItem(ItemEntity orderItem) {
        if (orderItem != null && itemEntities != null) {
            itemEntities.remove(orderItem);
            orderItem.setCustomer(null);
        }
    }
}
