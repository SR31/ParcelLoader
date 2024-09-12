package ru.liga.algorithms;

import ru.liga.parcel.Parcel;
import ru.liga.truck.Truck;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComplexLoadingAlgorithm implements LoadingAlgorithm {
    public List<Truck> run(List<Parcel> parcels) {
        List<Truck> trucks = new ArrayList<>() {{
            add(new Truck(6, 6));
        }};

        parcels = parcels.stream().sorted(
                Comparator.comparing(Parcel::getHeight)
                        .thenComparing(Parcel::getWidth)
        ).sorted((a, b) -> -1).toList();

        for (Parcel parcel : parcels) {
            boolean loaded = false;
            // Пытаемся загрузить посылку в уже существующие грузовики
            for (Truck truck : trucks) {
                if (truck.tryToLoadParcel(parcel)) {
                    loaded = true;
                    break;
                }
            }

            if (!loaded) {
                Truck newTruck = new Truck(6, 6);
                newTruck.tryToLoadParcel(parcel);
                trucks.add(newTruck);
            }
        }

        return trucks;
    }
}
