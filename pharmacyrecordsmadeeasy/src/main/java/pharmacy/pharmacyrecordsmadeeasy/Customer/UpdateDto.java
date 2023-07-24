package pharmacy.pharmacyrecordsmadeeasy.Customer;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import pharmacy.pharmacyrecordsmadeeasy.Response.Roles;

import java.time.LocalDateTime;

@Data
public class UpdateDto {

    private String name;
    private String address;
    private String contactInformation;
    private String username;
    private String gender;
    private String password;
    private String email;
    private Roles roles;
    @UpdateTimestamp
    private String updated;
}
