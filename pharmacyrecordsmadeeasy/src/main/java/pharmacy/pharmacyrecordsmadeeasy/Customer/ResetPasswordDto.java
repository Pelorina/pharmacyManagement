package pharmacy.pharmacyrecordsmadeeasy.Customer;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String usernameOrEmail;
    private String newPassword;
    private String email;
}
