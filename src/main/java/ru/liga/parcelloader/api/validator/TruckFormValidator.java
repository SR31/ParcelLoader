package ru.liga.parcelloader.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.liga.parcelloader.api.dto.truck.TruckDTO;

public class TruckFormValidator implements ConstraintValidator<TruckFormMustBePresent, TruckDTO> {
    @Override
    public boolean isValid(TruckDTO truckDTO, ConstraintValidatorContext constraintValidatorContext) {
        if (truckDTO.getGrid() != null) {
            if (!isTruckRightForm(truckDTO)) {
                return false;
            }

            if (isTruckContainZeroLengthArrays(truckDTO)) {
                return false;
            }

            if (truckDTO.getWidth() != null && truckDTO.getGrid()[0].length != truckDTO.getWidth()) {
                return false;
            }

            return truckDTO.getHeight() == null || truckDTO.getGrid().length == truckDTO.getHeight();
        } else {
            return truckDTO.getWidth() != null || truckDTO.getHeight() != null;
        }
    }

    private boolean isTruckRightForm(TruckDTO truckDTO) {
        if (truckDTO.getGrid().length == 0) {
            return false;
        }

        int firstLayerLength = truckDTO.getGrid()[0].length;

        for (int i = 1; i < truckDTO.getGrid().length; i++) {
            if (truckDTO.getGrid()[i].length != firstLayerLength) {
                return false;
            }
        }

        return true;
    }

    private boolean isTruckContainZeroLengthArrays(TruckDTO truckDTO) {
        for (int i = 0; i < truckDTO.getGrid().length; i++) {
            if (truckDTO.getGrid()[i].length == 0) {
                return true;
            }
        }

        return false;
    }
}
