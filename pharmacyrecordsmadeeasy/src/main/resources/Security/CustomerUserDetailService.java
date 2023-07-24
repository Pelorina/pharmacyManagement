package pharmacy.pharmacyrecordsmadeeasy.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pharmacy.pharmacyrecordsmadeeasy.Customer.CustomerEntity;
import pharmacy.pharmacyrecordsmadeeasy.Customer.CustomerRepo;

import java.util.Optional;
@Service
public class CustomerUserDetailService implements UserDetailsService {
    @Autowired
    CustomerRepo customerRepo;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<CustomerEntity> userInfo = Optional.ofNullable(customerRepo.findByName(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("user with provided credentials not found")));

//            Set<GrantedAuthority> authorities=user.getRoles().stream()
//                    .map((role) ->new SimpleGrantedAuthority(.getRoles())).collect(Collectors.toSet());
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found" + usernameOrEmail));
    }
}
