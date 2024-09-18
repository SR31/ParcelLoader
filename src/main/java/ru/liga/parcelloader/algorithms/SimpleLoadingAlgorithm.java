package ru.liga.parcelloader.algorithms;

import ru.liga.parcelloader.truck.Truck;
import ru.liga.parcelloader.parcel.Parcel;

import java.util.ArrayList;
import java.util.List;

public class SimpleLoadingAlgorithm implements LoadingAlgorithm {
    @Override
    public List<Truck> run(List<Parcel> parcels) {
        List<ru.liga.parcelloader.truck.Truck> trucks = new ArrayList<>();

        parcels.forEach(parcel -> {
            ru.liga.parcelloader.truck.Truck truck = new ru.liga.parcelloader.truck.Truck(6, 6);
            truck.tryToLoadParcel(parcel);
            trucks.add(truck);
        });

        return trucks;
    }
}