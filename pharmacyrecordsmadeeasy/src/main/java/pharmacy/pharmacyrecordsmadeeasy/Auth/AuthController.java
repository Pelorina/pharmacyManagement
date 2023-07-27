package pharmacy.pharmacyrecordsmadeeasy.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pharmacy.pharmacyrecordsmadeeasy.Customer.CustomerRepo;
import pharmacy.pharmacyrecordsmadeeasy.Customer.CustomerService;
import pharmacy.pharmacyrecordsmadeeasy.Customer.Dto;
import pharmacy.pharmacyrecordsmadeeasy.DTO.LoginDto;
import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;
import pharmacy.pharmacyrecordsmadeeasy.Pharmacist.PharmacistRepo;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private PharmacistRepo pharmacistRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthService authService;
    @Autowired
    CustomerService customerService;
//    @PostMapping("/signup")
//    public Response signup(@RequestBody Dto dto) {
//        return customerService.signup(dto);
//    }

     @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto){
        return  ResponseEntity.ok(authService.login(loginDto));
    }
}
