package pharmacy.pharmacyrecordsmadeeasy.Pharmacist;

import org.springframework.stereotype.Service;
import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;


public interface PharmacistService {
    Response createPharmacists(PharmacyRequest pharmacyRequest);
    Response findById(Long id);
    String deletePharmacy(Long id);
    Response updatePharDetails(PharmacyDetail detail);


}
