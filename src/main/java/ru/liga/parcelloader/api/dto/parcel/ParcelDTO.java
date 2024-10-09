package ru.liga.parcelloader.api.dto.parcel;

import lombok.Data;

@Data
public class ParcelDTO {
    private String name;
    private Character fillingSymbol;
    private Integer shapeId;
}
