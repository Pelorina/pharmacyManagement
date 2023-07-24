package pharmacy.pharmacyrecordsmadeeasy.Medicine;

import lombok.Data;
import org.springframework.stereotype.Service;
import pharmacy.pharmacyrecordsmadeeasy.DTO.Response;
import pharmacy.pharmacyrecordsmadeeasy.Response.ResponseUtils;

import java.util.Optional;

@Service
public class MedServiceImpl implements MedServices {
    private final MedicineRepository medicineRepository;

    public MedServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @Override
    public Response findById(Long id) {
        boolean existsById = medicineRepository.existsById(id);
        if (!existsById) {
            return Response.builder()
                    .responseCode(ResponseUtils.MEDICINE_ID_CODE)
                    .responseMessage(ResponseUtils.MEDICINE_ID_MESSAGE)
                    .data(null)
                    .build();
        } else {
            MedicineEntity findById = medicineRepository.findById(id).get();
            return Response.builder()
                    .responseCode(ResponseUtils.MEDICINE_ID_CODE)
                    .responseMessage(ResponseUtils.MEDICINE_ID_EXIST_MESSAGE)
                    .medData(MedData.builder()
                            .name(findById.getName())
                            .quantity(findById.getQuantity())
                            .price(findById.getPrice())
                            .build())
                    .build();

        }
    }

    @Override
    public int quantityLeftAfterOrder(Long id, int quantitySold) {
        MedicineEntity medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medicine not found"));

        int currentQuantity = medicine.getQuantity();
        int quantityLeft = currentQuantity - quantitySold;

        if (quantityLeft < 0) {
            quantityLeft = 0;
        }

        // Update the quantity of the medicine
        medicine.setQuantity(quantityLeft);
        medicineRepository.save(medicine);

        return quantityLeft;
    }

    @Override
    public Response update(MedUpdate medUpdate) {
        Optional<MedicineEntity> existingMedicine = medicineRepository.findByName(medUpdate.getName());

        if (existingMedicine.isPresent()) {
            MedicineEntity update = existingMedicine.get();
            update.setDescription(medUpdate.getDescription());
            update.setExpiryDate(medUpdate.getExpiryDate());
            update.setQuantity(medUpdate.getQuantity());
            update.setPrice(medUpdate.getPrice());

            MedicineEntity updatedStock = medicineRepository.save(update);

            return Response.builder()
                    .responseCode(ResponseUtils.MEDICINE_ID_CODE)
                    .responseMessage(ResponseUtils.UPDATE_SUCCESSFULLY_MESSAGE)
                    .medData(MedData.builder()
                            .name(updatedStock.getName())
                            .price(updatedStock.getPrice())
                            .quantity(updatedStock.getQuantity())
                            .build())
                    .build();
        } else {
            return Response.builder()
                    .responseCode(ResponseUtils.MEDICINE_ID_CODE)
                    .responseMessage(ResponseUtils.MEDICINE_ID_MESSAGE)
                    .data(null)
                    .build();
        }
    }


    @Override
    public Response createMedicine(Request request) {
        boolean existsByName = medicineRepository.existsByName(request.getName());
        if (existsByName) {
            return Response.builder()
                    .responseCode(ResponseUtils.MEDICINE_ID_CODE)
                    .responseMessage(ResponseUtils.MEDICINE_ID_EXIST_MESSAGE)
                    .data(null)
                    .build();
        } else {
            MedicineEntity create = MedicineEntity.builder()
                    .price(request.getPrice())
                    .name(request.getName())
                    .dosage(request.getDosage())
                    .quantity(request.getQuantity())
                    .description(request.getDescription())
                    .expiryDate(request.getExpiryDate())
                    .build();

            MedicineEntity stock = medicineRepository.save(create);

            return Response.builder()
                    .responseCode(ResponseUtils.MEDICINE_ID_CODE)
                    .responseMessage(ResponseUtils.MEDICINE_SUCCESSFULLY_CREATED_MESSAGE) // Corrected response message
                    .medData(MedData.builder()
                            .name(stock.getName())
                            .quantity(stock.getQuantity())
                            .price(stock.getPrice())
                            .build())
                    .build();
        }

    }
}
