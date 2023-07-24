package pharmacy.pharmacyrecordsmadeeasy.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
        List<OrderEntity> findByOrderNumber(String orderNumber);


}
