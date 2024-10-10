package ru.liga.parcelloader.api.dto.parcel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParcelDTO {
    private String name;
    private Character fillingSymbol;
    private Integer shapeId;
}
