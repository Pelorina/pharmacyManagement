package pharmacy.pharmacyrecordsmadeeasy.Response;

import lombok.Builder;
import pharmacy.pharmacyrecordsmadeeasy.OrderItem.ItemEntity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Random;

public class ResponseUtils {
    public static final String USER_EXISTS_CODE = "001";
    public static final String UPDATE_SUCCESSFULLY = "005";
    public static final String UPDATE_SUCCESSFULLY_MESSAGE = "you have successfully update your profile";
    public static final String USER_EXISTS_MESSAGE = "user exists";
    public static final String USER_CREATED_CODE = "006";
    public static final String USER_CREATED_MESSAGE = "successfully created";
    public static final String SUCCESSFUL_LOGIN_RESPONSE_CODE = "003";
    public static final String SUCCESSFUL_LOGIN_MESSAGE = "you've logged in successfully";
    public static final String SUCCESSFULLY_RESET_PASSWORD_CODE = "004";
    public static final String SUCCESSFULLY_RESET_PASSWORD_MESSAGE = "password successfully changed";
    public static final String USER_DOES_NOT_CODE_EXIST = "002";
    public static final String USER_DOES_NOT_MESSAGE = "user does not exists";
    public static final String MEDICINE_NOT_AVAILABLE_CODE = "007";
    public static final String MEDICINE_NOT_AVAILABLE_Message = "medicine not in stock you can preorder";
    public static final String MEDICINE_ID_CODE = "008";
    public static final String MEDICINE_ID_MESSAGE = "medicine id does not exists";
    public static final String MEDICINE_ID_EXIST_MESSAGE = "medicine exists";
    public static final String MEDICINE_PROFILE_MESSAGE = "medicine details has been updated";
    public static final String MEDICINE_SUCCESSFULLY_CREATED_MESSAGE = "medicine has been successfully added to the database";
    public static final String ORDER_CODE="009";
    public static final String ORDER_MESSAGE="order number does not exists";
    public static final String ORDER_FOUND_MESSAGE="order found below is the order details";
    public static final String ORDER_PLACED_MESSAGE="order has already been placed";
    public static final String ORDER_SUCCESS_MESSAGE="you order has been successfully placed";
    public static final String ORDER_CANCEL_SUCCESS_MESSAGE="you have successfully cancel your order";
    public static final String ORDER_CANCEL_FAILED_MESSAGE="sorry you can't cancel your order it's over cancel time";
    public static final String ERROR_CODE="010";

        private static final String ORDER_NUMBER_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        private static final int ORDER_NUMBER_LENGTH = 8; // Set the length to 8 characters

        public static long generateOrderNumber() {
            SecureRandom random = new SecureRandom();
            StringBuilder orderNumber = new StringBuilder();

            for (int i = 0; i < ORDER_NUMBER_LENGTH; i++) {
                int randomIndex = random.nextInt(ORDER_NUMBER_CHARS.length());
                char randomChar = ORDER_NUMBER_CHARS.charAt(randomIndex);
                orderNumber.append(randomChar);
            }

            String orderNumberString = orderNumber.toString();
            BigInteger bigIntegerOrderNumber = new BigInteger(orderNumberString, 36);

            // Limit the order number to a maximum value
            BigInteger maxOrderNumber = new BigInteger("99999999"); // Maximum value for 8 characters
            bigIntegerOrderNumber = bigIntegerOrderNumber.mod(maxOrderNumber);

            return bigIntegerOrderNumber.longValue();
        }



    public static String generateRandomUnitPrice() {
        double minPrice = 100.0;   // Minimum price in dollars
        double maxPrice = 1000.0; // Maximum price in dollars

        // Generate a random price within the specified range
        double randomPrice = minPrice + Math.random() * (maxPrice - minPrice);

        // Round the price to two decimal places and convert it to a string
        String unitPrice = String.format("%.2f", randomPrice);

        return unitPrice;
    }
    public static String calculateTotalAmount(ItemEntity item) {
        int quantity = item.getQuantity();
        BigDecimal unitPrice = item.getUnitPrice();

        BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(quantity));

        return totalAmount.toString();
    }



    public static void main(String[] args) {
        long orderNumber = generateOrderNumber();
        System.out.println("Generated Order Number: " + orderNumber);
        System.out.println(generateRandomUnitPrice());

    }
}