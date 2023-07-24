package pharmacy.pharmacyrecordsmadeeasy.OrderItem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class Controller {
    private final ItemService itemService;

    public Controller(ItemService itemService) {
        this.itemService = itemService;

    }

    @PostMapping("/order")
    public Response order(@RequestBody ItemRequest request) {
        return itemService.order(request);
    }

    @DeleteMapping("/{orderNumber}")
    public ResponseEntity<Response> cancel(@PathVariable Long orderNumber) {
        Response response = itemService.cancelOrder(orderNumber);
        // You can return a ResponseEntity with appropriate status and body
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{orderNumber}")
    public Response findById(
            @PathVariable(name = "orderNumber", required = true) Long orderNumber

    ) {
        return itemService.findByOrderNumber(orderNumber);
    }
}