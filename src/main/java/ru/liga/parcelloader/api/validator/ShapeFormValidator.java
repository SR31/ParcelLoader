package ru.liga.parcelloader.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolationException;
import ru.liga.parcelloader.api.dto.parcel.LayerDTO;
import ru.liga.parcelloader.api.dto.parcel.ShapeDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShapeFormValidator implements ConstraintValidator<ShapeMustBePresentedBySameSymbols, ShapeDTO> {
    @Override
    public boolean isValid(ShapeDTO shape, ConstraintValidatorContext context) {
        Set<Character> symbols = new HashSet<>();
        for (LayerDTO layerDTO : shape.getLayers()) {
            String layerContent = layerDTO.getContent();

            for (int i = 0; i < layerContent.length(); i++) {
                if (layerContent.charAt(i) == ' ') {
                    continue;
                }
                symbols.add(layerContent.charAt(i));

                if (symbols.size() > 1) {
                    return false;
                }
            }
        }

        return symbols.size() == 1;
    }
}
