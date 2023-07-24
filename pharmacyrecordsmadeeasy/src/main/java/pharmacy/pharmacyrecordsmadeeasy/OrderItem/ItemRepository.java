package pharmacy.pharmacyrecordsmadeeasy.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    boolean existsByOrderNumber(Long orderNumber);
    Optional<ItemEntity> findByOrderNumberOrOrderDate(Long orderNumber,Date orderDate);
    boolean existsByOrderDate(Date orderDate);
    Optional<ItemEntity> findByOrderNumber(Long orderNumber);

}
