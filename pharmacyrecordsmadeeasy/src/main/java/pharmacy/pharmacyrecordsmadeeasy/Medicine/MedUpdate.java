package pharmacy.pharmacyrecordsmadeeasy.Medicine;

import lombok.Data;

import java.util.Date;
@Data
public class MedUpdate {

    private String name;
    private String description;
    private String dosage;
    private Integer quantity;
    private String price;
    private Date expiryDate;
}
