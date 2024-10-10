package ru.liga.parcelloader.api.dto.parcel;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.liga.parcelloader.api.validator.ShapeMustBePresentedBySameSymbols;

import java.util.List;

@Data
@Builder
@ShapeMustBePresentedBySameSymbols
public class ShapeDTO {
    @NotEmpty(message = "Список слоев не должен быть пустым")
    private List<LayerDTO> layers;
}
