package ru.liga.parcelloader.api.dto.truck;

import jakarta.validation.constraints.Min;
import lombok.Data;
import ru.liga.parcelloader.api.validator.TruckFormMustBePresent;

@Data
@TruckFormMustBePresent
public class TruckDTO {
    @Min(1)
    private Integer width;
    @Min(1)
    private Integer height;
    private char[][] grid;
}
