package pharmacy.pharmacyrecordsmadeeasy.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllData {
    private String name;
    private String username;
    private String email;
}
