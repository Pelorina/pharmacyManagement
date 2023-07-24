package pharmacy.pharmacyrecordsmadeeasy.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pharmacy.pharmacyrecordsmadeeasy.Customer.CustomerRepo;
import pharmacy.pharmacyrecordsmadeeasy.DTO.LoginDto;
import pharmacy.pharmacyrecordsmadeeasy.Security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService{

    private AuthenticationManager authenticationManager;
    private CustomerRepo customerRepo;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, CustomerRepo customerRepo,
                           PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public AuthResponse login(LoginDto loginDto) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setToken(jwtTokenProvider.generateToken(authentication));
        return authResponse;
    }
}
