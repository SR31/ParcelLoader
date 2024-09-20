package ru.liga.parcelloader.algorithms;

import ru.liga.parcelloader.models.Parcel;
import ru.liga.parcelloader.models.Truck;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SameParcelsWeightAlgorithm extends MaxFillingTrucksAlgorithm {
    private final int maxTruckCount;

    public SameParcelsWeightAlgorithm(int maxTruckCount) {
        super();
        this.maxTruckCount = maxTruckCount;
    }

    @Override
    public List<Truck> run(List<Parcel> parcels) {
        List<Truck> trucks = new ArrayList<>();
        for (int i = 0; i < maxTruckCount; i++)
            trucks.add(new Truck());

        parcels = sortedByWidthAndHeight(parcels);

        parcelsLoop:
        for (Parcel parcel : parcels) {
            trucks.sort(Comparator.comparingInt(Truck::getWeight));
            for (Truck truck : trucks) {
                if (truck.loadParcel(parcel)) {
                    continue parcelsLoop;
                }
            }

            throw new IllegalArgumentException("Невозможно погрузить посылки");
        }


        return trucks;
    }
}
