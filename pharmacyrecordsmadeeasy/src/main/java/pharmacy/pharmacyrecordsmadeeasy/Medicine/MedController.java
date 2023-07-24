package pharmacy.pharmacyrecordsmadeeasy.Medicine;

import org.springframework.web.bind.annotation.*;
import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;

@RestController
@RequestMapping("/medicine")
public class MedController {
    private final MedServiceImpl service;

    public MedController(MedServiceImpl service) {
        this.service = service;
    }
    @PostMapping("/create")
    public Response create(@RequestBody Request request){
        return service.createMedicine(request);
    }
    @GetMapping("/{id}")
    public Response getById(@PathVariable(required = true,name = "id")@RequestBody Long id){
    return service.findById(id);
    }
    @PostMapping("/update")
    public Response restock(@RequestBody MedUpdate medUpdate){
        return service.update(medUpdate);
    }
    @GetMapping("/medicine/{id}/{quantitySold}")
    public int getQuantityLeftAfterOrder(@PathVariable Long id, @PathVariable("quantitySold") int quantitySold) {
        return service.quantityLeftAfterOrder(id, quantitySold);
    }

}
