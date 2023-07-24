package pharmacy.pharmacyrecordsmadeeasy.Medicine;

import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;

public interface MedServices {
Response findById(Long id);
public int quantityLeftAfterOrder(Long id,int QuantitySold);
Response update (MedUpdate medUpdate) ;
Response createMedicine(Request request);
}
