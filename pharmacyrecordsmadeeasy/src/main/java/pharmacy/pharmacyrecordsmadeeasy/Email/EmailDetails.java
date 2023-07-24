package pharmacy.pharmacyrecordsmadeeasy.Email;

import lombok.Data;

@Data
public class EmailDetails {
    private String recipient;
    private String subject;
    private String message;
    private String attachment;
}
