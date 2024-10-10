package ru.liga.parcelloader.api.dto.truck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.parcelloader.api.dto.parcel.ParcelDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrucksLoadingDTO {
    private List<ParcelDTO> parcels;
    private List<TruckDTO> trucks;
}
