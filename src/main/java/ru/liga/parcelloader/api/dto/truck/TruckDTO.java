package ru.liga.parcelloader.api.dto.truck;

import lombok.Data;

@Data
public class TruckDTO {
    private Integer width;
    private Integer height;
    private char[][] grid;
}
