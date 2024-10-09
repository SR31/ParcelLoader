package ru.liga.parcelloader.util.algorithms;

import org.springframework.stereotype.Component;
import ru.liga.parcelloader.type.annotation.TrucksGenerator;
import ru.liga.parcelloader.type.model.Truck;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;

import java.util.List;

@Component("oneToOne")
@TrucksGenerator
public class OneParcelOneTruckAlgorithm implements LoadingAlgorithm {
    /**
     * {@inheritDoc}
     * <br>
     * Погрузка одной посылки в одну машину
     * Машин при погрузке может быть неограниченное количество
     */
    @Override
    public List<Truck> load(List<Truck> trucks, List<Parcel> parcels) {
        trucks.clear();

        parcels.forEach(parcel -> {
            Truck truck = new Truck(6, 6);
            truck.loadParcel(parcel);
            trucks.add(truck);
        });

        return trucks;
    }
}