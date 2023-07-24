package pharmacy.pharmacyrecordsmadeeasy.Pharmacist;
import org.springframework.stereotype.Service;
import pharmacy.pharmacyrecordsmadeeasy.DTO.AllData;
import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;
import pharmacy.pharmacyrecordsmadeeasy.Email.EmailDetails;
import pharmacy.pharmacyrecordsmadeeasy.Email.EmailService;
import pharmacy.pharmacyrecordsmadeeasy.ReferralCode.ReferralCodeGenerator;
import pharmacy.pharmacyrecordsmadeeasy.Response.ResponseUtils;
import pharmacy.pharmacyrecordsmadeeasy.Response.Roles;
import pharmacy.pharmacyrecordsmadeeasy.VerificationCode.VerificationCodeGenerator;
@Service
public class PharmacistImpl implements PharmacistService{
    private final PharmacistRepo pharmacistRepo;
//    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationCodeGenerator verificationCodeGenerator;
    private final ReferralCodeGenerator referralCodeGenerator;

    public PharmacistImpl(PharmacistRepo pharmacistRepo, EmailService emailService, VerificationCodeGenerator verificationCodeGenerator, ReferralCodeGenerator referralCodeGenerator) {
        this.pharmacistRepo = pharmacistRepo;
//        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.verificationCodeGenerator = verificationCodeGenerator;
        this.referralCodeGenerator = referralCodeGenerator;
    }

    @Override
    public Response createPharmacists(PharmacyRequest pharmacyRequest) {
        {
            boolean existsByEmail = pharmacistRepo.existsByEmail(pharmacyRequest.getEmail());
            if (existsByEmail) {
                return Response.builder()
                        .responseMessage(ResponseUtils.USER_EXISTS_MESSAGE)
                        .responseCode(ResponseUtils.USER_EXISTS_CODE)
                        .data(null)
                        .build();
            } else {
                PharmacistEntity client = PharmacistEntity.builder()
                        .email(pharmacyRequest.getEmail())
                        .name(pharmacyRequest.getName())
                        .username(pharmacyRequest.getUsername())
                        .gender(pharmacyRequest.getGender())
//                        .password(passwordEncoder.encode(pharmacyRequest.getPassword()))
                        .password(pharmacyRequest.getPassword())
                        .address(pharmacyRequest.getAddress())
                        .roles(Roles.ADMIN)
                        .contactInformation(pharmacyRequest.getContactInformation())
                        .build();

                PharmacistEntity admin = pharmacistRepo.save(client);

                EmailDetails signupCustomer = new EmailDetails();
                signupCustomer.setRecipient(admin.getEmail());
                signupCustomer.setSubject("Account Creation Details");
                signupCustomer.setMessage("CONGRATULATIONS! You have successfully created an account with Health Made Easy." +
                        "\nYour account details:\nAccount Name: " + admin.getName() +
                        "\nYour Email is: " + admin.getEmail() +
                        "\nYour Referral code is: " + ReferralCodeGenerator.generateReferralCode());
                emailService.sendSimpleEmail(signupCustomer);

                return Response.builder()
                        .responseCode(ResponseUtils.USER_CREATED_CODE)
                        .responseMessage(ResponseUtils.USER_CREATED_MESSAGE)
                        .data( AllData.builder()
                                .name(admin.getName())
                                .username(admin.getUsername())
                                .email(admin.getEmail())
                                .build())
                        .build();
            }
        }

    }

    @Override
    public Response findById(Long id) {
        boolean existsById= pharmacistRepo.existsById(id);
        if(!pharmacistRepo.existsById(id)){
            return Response.builder()
                    .responseCode(ResponseUtils.USER_DOES_NOT_CODE_EXIST)
                    .responseMessage(ResponseUtils.USER_DOES_NOT_MESSAGE)
                    .data(null)
                    .build();
        }else {
            PharmacistEntity findById =pharmacistRepo.findById(id).get();
            return Response.builder()
                    .responseCode(ResponseUtils.USER_EXISTS_CODE)
                    .responseMessage(ResponseUtils.USER_EXISTS_MESSAGE)
                    .data( AllData.builder()
                            .name(findById.getName())
                            .email(findById.getEmail())
                            .username(findById.getUsername())
                            .build())
                    .build();
        }
    }

    @Override
    public String deletePharmacy(Long id) {
        // Check if the pharmacist exists
        if (!pharmacistRepo.existsById(id)) {
            return "Pharmacist with ID " + id + " not found. Deletion failed.";
        }
        PharmacistEntity pharmacistToDelete = pharmacistRepo.findById(id).get();
        pharmacistRepo.delete(pharmacistToDelete);

        return "Pharmacist with ID " + id + " has been deleted successfully.";
    }



    @Override
    public Response updatePharDetails(PharmacyDetail detail) {
        boolean existsByName =pharmacistRepo.existsByEmail(detail.getName());
        if(!pharmacistRepo.existsByEmail(detail.getName())){
            return Response.builder()
                    .responseCode(ResponseUtils.USER_DOES_NOT_CODE_EXIST)
                    .responseMessage(ResponseUtils.USER_DOES_NOT_MESSAGE)
                    .data(null)
                    .build();
        }else{
            PharmacistEntity existingUser=pharmacistRepo.findByEmail(detail.getEmail()).get();
            existingUser.setPassword(detail.getPassword());
            existingUser.setAddress(detail.getAddress());
            existingUser.setName(detail.getName());
            existingUser.setUsername(detail.getUsername());
            existingUser.setEmail(detail.getEmail());
            existingUser.setContactInformation(detail.getContactInformation());
            existingUser.setGender(detail.getGender());
            existingUser.setRoles(Roles.ADMIN);

            PharmacistEntity update=pharmacistRepo.save(existingUser);
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
}
