package pharmacy.pharmacyrecordsmadeeasy.OrderItem;

import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;

import java.util.Date;


public interface ItemService {
    Response order(ItemRequest request);
    Response findByOrderNumber(Long orderNumber);
    Response cancelOrder(Long orderNumber);


}
