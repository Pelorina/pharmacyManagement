package pharmacy.pharmacyrecordsmadeeasy.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pharmacy.pharmacyrecordsmadeeasy.Customer.CustomerEntity;
import pharmacy.pharmacyrecordsmadeeasy.Customer.CustomerRepo;
import pharmacy.pharmacyrecordsmadeeasy.Pharmacist.PharmacistEntity;
import pharmacy.pharmacyrecordsmadeeasy.Pharmacist.PharmacistRepo;
import pharmacy.pharmacyrecordsmadeeasy.Response.Roles;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerDetailService implements UserDetailsService {

    private final CustomerRepo customerRepo;
    private final PharmacistRepo pharmacistRepo;

    public CustomerDetailService(CustomerRepo customerRepo, PharmacistRepo pharmacistRepo) {
        this.customerRepo = customerRepo;
        this.pharmacistRepo = pharmacistRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        CustomerEntity customer = customerRepo.findByEmailOrUsername(usernameOrEmail, usernameOrEmail).orElse(null);
        PharmacistEntity pharmacist = pharmacistRepo.findByEmailOrUsername(usernameOrEmail, usernameOrEmail).orElse(null);

        if (customer == null && pharmacist == null) {
            throw new UsernameNotFoundException("User with provided credentials not found");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();

        if (customer != null) {
            // Handle authorities for CustomerEntity (e.g., "ROLE_USER")
            authorities.add(new SimpleGrantedAuthority(Roles.ROLE_USER.name()));
        }

        if (pharmacist != null) {
            // Handle authorities for PharmacistEntity (e.g., "ROLE_ADMIN")
            authorities.add(new SimpleGrantedAuthority(Roles.ROLE_ADMIN.name()));
        }

        String password = (customer != null) ? customer.getPassword() : pharmacist.getPassword();

        // Use UserDetails interface instead of concrete User class
        return User.withUsername((customer != null) ? customer.getUsername() : pharmacist.getUsername())
                .password(password)
                .authorities(authorities)
                .build();
    }
}
