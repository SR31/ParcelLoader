package ru.liga.parcelloader.algorithms;

import ru.liga.parcelloader.truck.Truck;
import ru.liga.parcelloader.parcel.Parcel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComplexLoadingAlgorithm implements LoadingAlgorithm {
    public List<Truck> run(List<Parcel> parcels) {
        List<ru.liga.parcelloader.truck.Truck> trucks = new ArrayList<>() {{
            add(new ru.liga.parcelloader.truck.Truck(6, 6));
        }};

        parcels = parcels.stream().sorted(
                Comparator.comparing(Parcel::getHeight)
                        .thenComparing(Parcel::getWidth)
        ).sorted((a, b) -> -1).toList();

        for (Parcel parcel : parcels) {
            boolean loaded = false;
            for (ru.liga.parcelloader.truck.Truck truck : trucks) {
                if (truck.tryToLoadParcel(parcel)) {
                    loaded = true;
                    break;
                }
            }

            if (!loaded) {
                ru.liga.parcelloader.truck.Truck newTruck = new ru.liga.parcelloader.truck.Truck(6, 6);
                newTruck.tryToLoadParcel(parcel);
                trucks.add(newTruck);
            }
        }

        return trucks;
    }
}
