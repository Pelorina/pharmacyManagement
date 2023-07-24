package pharmacy.pharmacyrecordsmadeeasy.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pharmacy.pharmacyrecordsmadeeasy.Medicine.MedData;
import pharmacy.pharmacyrecordsmadeeasy.OrderItem.OrderDataList;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Response {
    private String responseCode;
    private String responseMessage;
    private AllData data ;
    private MedData medData;
    private OrderDataList orderDataList;
}
