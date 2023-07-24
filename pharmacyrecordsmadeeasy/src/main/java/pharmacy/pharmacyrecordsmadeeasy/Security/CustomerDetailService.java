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
import java.util.stream.Collectors;

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
        CustomerEntity customer = customerRepo.findByEmailOrUsername(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User with provided credentials not found"));

        PharmacistEntity pharmacist = pharmacistRepo.findByEmailOrUsername(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User with provided credentials not found"));

        Set<GrantedAuthority> authorities = new HashSet<>();

        // Handle authorities for CustomerEntity (e.g., "ROLE_USER")
        authorities.add(new SimpleGrantedAuthority(Roles.USER.toString()));


//        if (customer != null) {
//            // Handle authorities for CustomerEntity (e.g., "ROLE_USER")
//            authorities.add(new SimpleGrantedAuthority(Roles.USER.toString()));
//        }


//        if (pharmacist != null) {
//            // Handle authorities for PharmacistEntity (e.g., "ROLE_ADMIN")
//            authorities.add(new SimpleGrantedAuthority(Roles.ADMIN.toString()));
//        }

        return new User(customer.getUsername(), customer.getPassword(), authorities);
    }


}
