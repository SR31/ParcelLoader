package ru.liga.algorithms;

import ru.liga.parcel.Parcel;
import ru.liga.truck.Truck;

import java.util.List;

public interface LoadingAlgorithm {
    List<Truck> run(List<Parcel> parcels);
}
