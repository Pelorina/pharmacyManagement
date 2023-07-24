package pharmacy.pharmacyrecordsmadeeasy.Pharmacist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PharmacyRequest {
    private String name;
    private String username;
    private String password;
    private String email;
    private String gender;
    private String address;
    private String contactInformation;
}
