package pharmacy.pharmacyrecordsmadeeasy.Medicine;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicineRepository extends JpaRepository<MedicineEntity,Long>{
boolean existsById(Long id);

    boolean existsByName(String name);
Optional<MedicineEntity> findById(Long id);
Optional<MedicineEntity> findByName(String name);
}