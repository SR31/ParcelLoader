package ru.liga.parcelloader.counters;

import ru.liga.parcelloader.models.Parcel;
import ru.liga.parcelloader.models.Truck;

import java.util.Map;

public interface ParcelCounter {
    Map<Integer, Integer> countParcelsInTruck(Truck truck);
    Parcel getParcelById(int id);
}
