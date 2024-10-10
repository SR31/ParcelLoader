package ru.liga.parcelloader.api.dto.parcel;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.parcelloader.api.validator.ShapeMustBePresentedBySameSymbols;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ShapeMustBePresentedBySameSymbols
public class ShapeDTO {
    @NotEmpty(message = "Список слоев не должен быть пустым")
    private List<LayerDTO> layers;
}
