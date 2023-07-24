package pharmacy.pharmacyrecordsmadeeasy.VerificationCode;

import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class VerificationCodeGenerator {

        private static final String CHARACTERS = "0123456789";
        private static final int CODE_LENGTH = 6;

        public static String generateVerificationCode() {
            StringBuilder codeBuilder = new StringBuilder();

            Random random = new Random();
            for (int i = 0; i < CODE_LENGTH; i++) {
                int randomIndex = random.nextInt(CHARACTERS.length());
                char randomChar = CHARACTERS.charAt(randomIndex);
                codeBuilder.append(randomChar);
            }

            return codeBuilder.toString();
        }

    public static void main(String[] args) {
        String verificationCode = VerificationCodeGenerator.generateVerificationCode();
        System.out.println("Verification Code: " + verificationCode);

    }
    }


