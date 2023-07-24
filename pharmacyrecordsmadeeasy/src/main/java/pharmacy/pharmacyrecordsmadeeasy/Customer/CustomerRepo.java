package pharmacy.pharmacyrecordsmadeeasy.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<CustomerEntity,Long> {
    boolean existsById(Long id);
    Optional<CustomerEntity> findById(Long id);
//    boolean existsByEmailOrUsername(String email, String username);
    boolean existsByName(String name);
    Optional<CustomerEntity> findByName(String name);
    Optional<CustomerEntity> findByEmailOrUsername(String email, String username);
boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<CustomerEntity> findByEmail(String email);
    Optional<CustomerEntity> findByUsername(String username);

//    Optional<CustomerEntity> findByEmailOrUsername ( String email,String username);
}


