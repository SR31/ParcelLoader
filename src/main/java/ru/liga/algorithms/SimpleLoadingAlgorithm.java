package ru.liga.algorithms;

import ru.liga.parcel.Parcel;
import ru.liga.truck.Truck;

import java.util.ArrayList;
import java.util.List;

public class SimpleLoadingAlgorithm implements LoadingAlgorithm {
    @Override
    public List<Truck> run(List<Parcel> parcels) {
        List<Truck> trucks = new ArrayList<>();

        parcels.forEach(parcel -> {
            Truck truck = new Truck(6, 6);
            truck.tryToLoadParcel(parcel);
            trucks.add(truck);
        });

        return trucks;
    }
}