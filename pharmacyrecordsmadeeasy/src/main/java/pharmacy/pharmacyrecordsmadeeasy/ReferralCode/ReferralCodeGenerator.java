package pharmacy.pharmacyrecordsmadeeasy.ReferralCode;

import org.springframework.stereotype.Service;
import pharmacy.pharmacyrecordsmadeeasy.VerificationCode.VerificationCodeGenerator;

import java.util.Random;
@Service
public class ReferralCodeGenerator {
        private static final int CODE_LENGTH = 6;

        public static int generateReferralCode() {
            Random random = new Random();
            int min = (int) Math.pow(10, CODE_LENGTH - 1);
            int max = (int) Math.pow(10, CODE_LENGTH) - 1;
            return random.nextInt(max - min + 1) + min;
        }

    public static void main(String[] args) {
        int referralCode = ReferralCodeGenerator.generateReferralCode();
        System.out.println("referral Code: " + referralCode);

    }
    }


