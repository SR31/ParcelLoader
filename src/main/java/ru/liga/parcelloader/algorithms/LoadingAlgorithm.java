package ru.liga.parcelloader.algorithms;

import ru.liga.parcelloader.truck.Truck;
import ru.liga.parcelloader.parcel.Parcel;

import java.util.List;

public interface LoadingAlgorithm {
    List<Truck> run(List<Parcel> parcels);
}
