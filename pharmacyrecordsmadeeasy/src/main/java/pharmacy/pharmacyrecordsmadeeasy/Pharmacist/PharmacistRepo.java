package pharmacy.pharmacyrecordsmadeeasy.Pharmacist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PharmacistRepo extends JpaRepository<PharmacistEntity,Long> {
    boolean existsById(Long id);
    Optional<PharmacistEntity>findById(Long id);
    boolean existsByEmail(String email);
    Optional<PharmacistEntity>findByEmail(String email);
    Optional<PharmacistEntity> findByEmailOrUsername(String email,String username);

}
