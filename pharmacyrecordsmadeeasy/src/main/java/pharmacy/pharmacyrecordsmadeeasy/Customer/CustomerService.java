package pharmacy.pharmacyrecordsmadeeasy.Customer;

import pharmacy.pharmacyrecordsmadeeasy.DTO.LoginDto;
import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;

import java.util.List;

public interface CustomerService {
    Response signup(Dto dto);

    Response update (UpdateDto updateDto);
    public List<CustomerEntity> getAll();
    Response findById(Long id);
    Response setPassword (ResetPasswordDto resetDto);




}
