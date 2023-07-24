package pharmacy.pharmacyrecordsmadeeasy.Auth;

import pharmacy.pharmacyrecordsmadeeasy.DTO.LoginDto;

public interface AuthService {
    AuthResponse login (LoginDto loginDto);
}