package pharmacy.pharmacyrecordsmadeeasy.Customer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy.pharmacyrecordsmadeeasy.DTO.LoginDto;
import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping("/signup")
    public Response signup(@RequestBody Dto dto) {
        return customerService.signup(dto);
    }
//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginDto loginDto){
//        return ResponseEntity.ok(customerService.login(loginDto));
//    }
    @GetMapping("/all")
    public List<CustomerEntity>getAll(){
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public  Response findById (@PathVariable(name = "id",required = true)@RequestBody long id){
        return customerService.findById(id);
    }

    @PutMapping ("/update")
    public Response update(@RequestBody UpdateDto updateDto){
        return customerService.update(updateDto);
    }

    @PostMapping("/reset")
    public Response resetPassword(@RequestBody ResetPasswordDto passwordDto){
        return customerService.setPassword(passwordDto);
    }
}
