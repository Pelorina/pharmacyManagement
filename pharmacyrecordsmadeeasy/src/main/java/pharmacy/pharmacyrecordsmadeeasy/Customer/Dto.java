package pharmacy.pharmacyrecordsmadeeasy.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pharmacy.pharmacyrecordsmadeeasy.Response.Roles;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dto {
    private String name;
    private String address;
    private String contactInformation;
    private String username;
    private String gender;
    private String password;
    private String email;
    private Roles roles;
}
