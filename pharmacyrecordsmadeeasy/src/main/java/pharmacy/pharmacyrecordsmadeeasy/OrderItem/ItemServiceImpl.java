package pharmacy.pharmacyrecordsmadeeasy.OrderItem;

import org.springframework.stereotype.Service;
import pharmacy.pharmacyrecordsmadeeasy.Customer.CustomerEntity;
import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;
import pharmacy.pharmacyrecordsmadeeasy.Order.OrderEntity;
import pharmacy.pharmacyrecordsmadeeasy.Response.ResponseUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Response order(ItemRequest request) {
        List<OrderEntity> orderItems = request.getOrder(); // Assuming that the 'getOrder()' method returns the list of order items.

        if (orderItems == null || orderItems.isEmpty()) {
            // Treat as a single item order
            BigDecimal minPrice = request.getMinPrice() != null ? request.getMinPrice() : BigDecimal.ZERO;
            BigDecimal maxPrice = request.getMaxPrice() != null ? request.getMaxPrice() : BigDecimal.ZERO;

            BigDecimal randomUnitPrice = generateRandomUnitPrice(minPrice, maxPrice);
            BigDecimal totalPrice = randomUnitPrice.multiply(BigDecimal.valueOf(request.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);

            Long orderNumber = ResponseUtils.generateOrderNumber(); // Call the method in ResponseUtils to generate the order number

            ItemEntity placeOrder = ItemEntity.builder()
                    .orderNumber(ResponseUtils.generateOrderNumber()) // Set the generated order number
                    .medicineName(request.getMedicineName())
                    .unitPrice(randomUnitPrice)
                    .totalPrice(totalPrice)
                    .quantity(request.getQuantity())
                    .build();

            ItemEntity savedItem = itemRepository.save(placeOrder);

            return Response.builder()
                    .responseCode(ResponseUtils.ORDER_CODE)
                    .responseMessage(ResponseUtils.ORDER_SUCCESS_MESSAGE)
                    .orderDataList(OrderDataList.builder()
                            .orderNumber(savedItem.getOrderNumber()) // Include the order number in the response
                            .medicineName(savedItem.getMedicineName())
                            .unitPrice(savedItem.getUnitPrice())
                            .totalPrice(savedItem.getTotalPrice())
                            .quantity(savedItem.getQuantity())
                            .build())
                    .build();
        } else {
            // Treat as a multiple items order
            List<ItemEntity> orderedItems = new ArrayList<>();

            for (OrderEntity orderItem : orderItems) {
                BigDecimal randomUnitPrice = generateRandomUnitPrice(request.getMinPrice(), request.getMaxPrice());
                BigDecimal totalPrice = randomUnitPrice.multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                        .setScale(2, RoundingMode.HALF_UP);

                ; // Call the method in ResponseUtils to generate the order number

                ItemEntity placeOrder = ItemEntity.builder()
                        .orderNumber(ResponseUtils.generateOrderNumber()) // Set the generated order number
                        .medicineName(orderItem.getMedicineName())
                        .unitPrice(randomUnitPrice)
                        .totalPrice(totalPrice)
                        .quantity(Math.toIntExact(orderItem.getQuantity()))
                        .build();

                orderedItems.add(placeOrder);
            }

            List<ItemEntity> savedItems = itemRepository.saveAll(orderedItems);

            List<OrderDataList> orderDataList = savedItems.stream()
                    .map(item -> OrderDataList.builder()
                            .orderNumber(item.getOrderNumber()) // Include the order number in the response
                            .medicineName(item.getMedicineName())
                            .unitPrice(item.getUnitPrice())
                            .totalPrice(item.getTotalPrice())
                            .quantity(item.getQuantity())
                            .build())
                    .collect(Collectors.toList());

            return Response.builder()
                    .responseCode(ResponseUtils.ORDER_CODE)
                    .responseMessage(ResponseUtils.ORDER_SUCCESS_MESSAGE)
                    .orderDataList((OrderDataList) orderDataList)
                    .build();
        }
    }



    @Override
    public Response findByOrderNumber(Long orderNumber) {
        boolean existsByOrderNumber = itemRepository.existsByOrderNumber(orderNumber);
        if (!existsByOrderNumber) {
            return Response.builder()
                    .responseCode(ResponseUtils.ORDER_CODE)
                    .responseMessage(ResponseUtils.ORDER_MESSAGE)
                    .data(null)
                    .build();
        }

        Optional<ItemEntity> optionalItem = itemRepository.findByOrderNumber(orderNumber);
        if (optionalItem.isPresent()) {
            ItemEntity recheck = optionalItem.get();
            return Response.builder()
                    .responseCode(ResponseUtils.ORDER_CODE)
                    .responseMessage(ResponseUtils.ORDER_FOUND_MESSAGE)
                    .orderDataList(OrderDataList.builder()
                            .orderNumber(orderNumber)
                            .medicineName(recheck.getMedicineName())
                            .unitPrice(recheck.getUnitPrice())
                            .quantity(recheck.getQuantity())
                            .totalPrice(recheck.getTotalPrice())
                            .build())
                    .build();
        } else {
            return Response.builder()
                    .responseCode(ResponseUtils.ORDER_CODE)
                    .responseMessage("Order not found")
                    .data(null)
                    .build();
        }
    }


    @Override
    public Response cancelOrder(Long orderNumber) {
        Optional<ItemEntity> optionalOrder = itemRepository.findByOrderNumber(orderNumber);

        if (optionalOrder.isEmpty()) {
            return Response.builder()
                    .responseCode(ResponseUtils.ORDER_CODE)
                    .responseMessage(ResponseUtils.ORDER_MESSAGE)
                    .data(null)
                    .build();
        }

        ItemEntity order = optionalOrder.get();
        Date orderPlacedTime = order.getCreatedAt();

        if (orderPlacedTime == null) {
            return Response.builder()
                    .responseCode(ResponseUtils.ERROR_CODE)
                    .responseMessage("Order placed time is not available")
                    .data(null)
                    .build();
        }

        long minutesElapsed = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - orderPlacedTime.getTime());

        if (minutesElapsed < 1) {
            itemRepository.delete(order);
            return Response.builder()
                    .responseCode(ResponseUtils.ORDER_CODE)
                    .responseMessage(ResponseUtils.ORDER_CANCEL_SUCCESS_MESSAGE)
                    .data(null)
                    .build();
        } else if (minutesElapsed <= 20) {
            itemRepository.delete(order);
            // Handle orders within the 1-20 minute window (optional)
            return Response.builder()
                    .responseCode(ResponseUtils.ORDER_CODE)
                    .responseMessage(ResponseUtils.ORDER_CANCEL_SUCCESS_MESSAGE)
                    .data(null)
                    .build();
        } else {
            return Response.builder()
                    .responseCode("009")
                    .responseMessage("Sorry, you can't cancel your order. It's over the cancel time.")
                    .data(null)
                    .build();
        }
    }







    // Method to generate random unit price in the specified range
    private BigDecimal generateRandomUnitPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        BigDecimal random = new BigDecimal(Math.random());
        BigDecimal range = maxPrice.subtract(minPrice);
        BigDecimal randomUnitPrice = random.multiply(range).add(minPrice);
        return randomUnitPrice.setScale(2, RoundingMode.HALF_UP);
    }
}
