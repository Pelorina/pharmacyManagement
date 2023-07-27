package pharmacy.pharmacyrecordsmadeeasy.Customer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pharmacy.pharmacyrecordsmadeeasy.DTO.AllData;
import pharmacy.pharmacyrecordsmadeeasy.DTO.LoginDto;
import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;
import pharmacy.pharmacyrecordsmadeeasy.Email.EmailDetails;
import pharmacy.pharmacyrecordsmadeeasy.Email.EmailService;
import pharmacy.pharmacyrecordsmadeeasy.ReferralCode.ReferralCodeGenerator;
import pharmacy.pharmacyrecordsmadeeasy.Response.ResponseUtils;
import pharmacy.pharmacyrecordsmadeeasy.Response.Roles;
import pharmacy.pharmacyrecordsmadeeasy.VerificationCode.VerificationCodeGenerator;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceImpl implements CustomerService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomerRepo customerRepo;
    private final EmailService emailService;
    private final VerificationCodeGenerator verificationCodeGenerator;
    private final ReferralCodeGenerator referralCodeGenerator;




    public ServiceImpl(CustomerRepo customerRepo, EmailService emailService, VerificationCodeGenerator verificationCodeGenerator, ReferralCodeGenerator referralCodeGenerator, BCryptPasswordEncoder passwordEncoder){
        this.customerRepo = customerRepo;
        this.emailService = emailService;
        this.verificationCodeGenerator = verificationCodeGenerator;
        this.referralCodeGenerator = referralCodeGenerator;
        this.passwordEncoder = passwordEncoder;

//        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Response signup(Dto dto) {
        boolean existsByEmail = customerRepo.existsByEmail(dto.getEmail());
        if (existsByEmail) {
            return Response.builder()
                    .responseMessage(ResponseUtils.USER_EXISTS_MESSAGE)
                    .responseCode(ResponseUtils.USER_EXISTS_CODE)
                    .data(null)
                    .build();
        } else {
            CustomerEntity client = CustomerEntity.builder()
                    .email(dto.getEmail())
                    .name(dto.getName())
                    .username(dto.getUsername())
                    .gender(dto.getGender())
                    .password(passwordEncoder.encode(dto.getPassword()))
//                    .password(dto.getPassword())
                    .address(dto.getAddress())
                    .roles(Roles.ROLE_USER)
                    .contactInformation(dto.getContactInformation())
                    .build();

            CustomerEntity customer = customerRepo.save(client);

            EmailDetails signupCustomer = new EmailDetails();
            signupCustomer.setRecipient(customer.getEmail());
            signupCustomer.setSubject("Account Creation Details");
            signupCustomer.setMessage("CONGRATULATIONS! You have successfully created an account with Health Made Easy." +
                    "\nYour account details:\nAccount Name: " + customer.getName() +
                    "\nYour Email is: " + customer.getEmail() +
                    "\nYour Referral code is: " + ReferralCodeGenerator.generateReferralCode());
            emailService.sendSimpleEmail(signupCustomer);

            return Response.builder()
                    .responseCode(ResponseUtils.USER_CREATED_CODE)
                    .responseMessage(ResponseUtils.USER_CREATED_MESSAGE)
                    .data( AllData.builder()
                            .name(customer.getName())
                            .username(customer.getUsername())
                            .email(customer.getEmail())
                            .build())
                    .build();
        }
    }



    @Override
    public Response update(UpdateDto updateDto) {
        boolean existsByName =customerRepo.existsByName(updateDto.getName());
        if(!customerRepo.existsByName(updateDto.getName())){
            return Response.builder()
                    .responseCode(ResponseUtils.USER_DOES_NOT_CODE_EXIST)
                    .responseMessage(ResponseUtils.USER_DOES_NOT_MESSAGE)
                    .data(null)
                    .build();
        }else{
            CustomerEntity existingUser=customerRepo.findByName(updateDto.getName()).get();
            existingUser.setPassword(passwordEncoder.encode(updateDto.getPassword()));
            existingUser.setAddress(updateDto.getAddress());
            existingUser.setName(updateDto.getName());
            existingUser.setUsername(updateDto.getUsername());
            existingUser.setEmail(updateDto.getEmail());
            existingUser.setContactInformation(updateDto.getContactInformation());
            existingUser.setGender(updateDto.getGender());
            existingUser.setRoles(Roles.ROLE_USER);

            CustomerEntity update=customerRepo.save(existingUser);
            return Response.builder()
                    .responseCode(ResponseUtils.UPDATE_SUCCESSFULLY)
                    .responseMessage(ResponseUtils.UPDATE_SUCCESSFULLY_MESSAGE)
                    .data( AllData.builder()
                            .email(update.getEmail())
                            .username(update.getUsername())
                            .name(update.getName())
                            .build())
                    .build();
        }
    }


    @Override
    public List<CustomerEntity> getAll() {
        return customerRepo.findAll();
    }

    @Override
    public Response findById(Long id) {
        boolean existsById= customerRepo.existsById(id);
        if(!customerRepo.existsById(id)){
            return Response.builder()
                    .responseCode(ResponseUtils.USER_DOES_NOT_CODE_EXIST)
                    .responseMessage(ResponseUtils.USER_DOES_NOT_MESSAGE)
                    .data(null)
                    .build();
        }else {
            CustomerEntity findById =customerRepo.findById(id).get();
            return Response.builder()
                    .responseCode(ResponseUtils.USER_EXISTS_CODE)
                    .responseMessage(ResponseUtils.USER_EXISTS_MESSAGE)
                    .data(AllData.builder()
                            .name(findById.getName())
                            .email(findById.getEmail())
                            .username(findById.getUsername())
                            .build())
                    .build();
        }
    }



        @Override
        public Response setPassword(ResetPasswordDto resetDto) {
            String usernameOrEmail = resetDto.getUsernameOrEmail();

            Optional<CustomerEntity> userByEmail = customerRepo.findByEmail(usernameOrEmail);
            Optional<CustomerEntity> userByUsername = customerRepo.findByUsername(usernameOrEmail);

            Optional<CustomerEntity> user = userByEmail.or(() -> userByUsername);

            if (user.isEmpty()) {
                return Response.builder()
                        .responseCode(ResponseUtils.USER_DOES_NOT_CODE_EXIST)
                        .responseMessage(ResponseUtils.USER_DOES_NOT_MESSAGE)
                        .data(null)
                        .build();
            }

            // Handle the unique result
            user.get().setPassword(resetDto.getNewPassword());

            customerRepo.save(user.get());
            EmailDetails resetPassword = new EmailDetails();
            // ... (rest of the email sending code)

            return Response.builder()
                    .responseCode(ResponseUtils.SUCCESSFULLY_RESET_PASSWORD_CODE)
                    .responseMessage(ResponseUtils.SUCCESSFULLY_RESET_PASSWORD_MESSAGE)
                    .build();
        }
    }




