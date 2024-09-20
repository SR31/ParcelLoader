package ru.liga.parcelloader.algorithms;

import ru.liga.parcelloader.models.Truck;
import ru.liga.parcelloader.models.Parcel;

import java.util.ArrayList;
import java.util.List;

public class OneParcelOneTruckAlgorithm implements LoadingAlgorithm {
    @Override
    public List<Truck> run(List<Parcel> parcels) {
        List<Truck> trucks = new ArrayList<>();

        parcels.forEach(parcel -> {
            Truck truck = new Truck(6, 6);
            truck.loadParcel(parcel);
            trucks.add(truck);
        });

        return trucks;
    }
}