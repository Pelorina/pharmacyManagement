package pharmacy.pharmacyrecordsmadeeasy.Pharmacist;

import jakarta.persistence.*;
import lombok.*;
import pharmacy.pharmacyrecordsmadeeasy.Response.Roles;

@jakarta.persistence.Entity
@Setter
@Getter
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PharmacistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String address;
    private String gender;
    private String contactInformation;
    @Enumerated(EnumType.STRING)
    private Roles roles;
}
