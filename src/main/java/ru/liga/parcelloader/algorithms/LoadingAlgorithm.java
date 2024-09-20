package ru.liga.parcelloader.algorithms;

import ru.liga.parcelloader.models.Truck;
import ru.liga.parcelloader.models.Parcel;

import java.util.List;

public interface LoadingAlgorithm {
    List<Truck> run(List<Parcel> parcels);
}
