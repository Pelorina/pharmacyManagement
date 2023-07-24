package pharmacy.pharmacyrecordsmadeeasy.Pharmacist;

import org.springframework.web.bind.annotation.*;
import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;

@RestController
@RequestMapping("/api/admin")
public class PharmacistController {
    private final PharmacistService service;

    public PharmacistController(PharmacistService service) {
        this.service = service;
    }
    @PostMapping("/create")
    public Response create(@RequestBody PharmacyRequest request){
        return service.createPharmacists(request);
    }
    @DeleteMapping("/{id}")
    public String delete (@PathVariable Long id){
        return service.deletePharmacy(id);
    }
    @GetMapping("/get/{id}")
    public Response findById(@PathVariable Long id ){
        return service.findById(id);
    }
    @PutMapping("/update")
    public Response update (@RequestBody PharmacyDetail detail){
        return service.updatePharDetails(detail);
    }
}
