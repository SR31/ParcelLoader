package ru.liga.parcelloader.api.dto.parcel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParcelDTO {
    private String name;
    private Character fillingSymbol;
    private Integer shapeId;
}
