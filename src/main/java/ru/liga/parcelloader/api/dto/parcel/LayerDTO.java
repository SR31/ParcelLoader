package ru.liga.parcelloader.api.dto.parcel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LayerDTO {
    @NotBlank(message = "Слой не должен быть пустым")
    @Size(min = 1, max = 255, message = "Длина содержимого слоя должна быть в пределах от 1 до 255 (включительно)")
    private String content;
    private Integer shapeId;
}
