package ru.liga.parcelloader.util.algorithms;

import ru.liga.parcelloader.type.model.Truck;
import ru.liga.parcelloader.type.model.Parcel;

import java.util.ArrayList;
import java.util.List;

public class OneParcelOneTruckAlgorithm implements LoadingAlgorithm {
    /**
     * {@inheritDoc}
     * <br>
     * Погрузка одной посылки в одну машину
     * Машин при погрузке может быть неограниченное количество
     */
    @Override
    public List<Truck> load(List<Parcel> parcels) {
        List<Truck> trucks = new ArrayList<>();

        parcels.forEach(parcel -> {
            Truck truck = new Truck(6, 6);
            truck.loadParcel(parcel);
            trucks.add(truck);
        });

        return trucks;
    }
}