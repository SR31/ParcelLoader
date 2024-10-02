package ru.liga.parcelloader.algorithms;

import ru.liga.parcelloader.models.Parcel;
import ru.liga.parcelloader.models.Truck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SameParcelsWeightAlgorithm implements LoadingAlgorithm {
    private final int maxTruckCount;

    /**
     *
     * @param maxTruckCount максимальное количество машин, куда будут погружены посылки
     */
    public SameParcelsWeightAlgorithm(int maxTruckCount) {
        super();
        this.maxTruckCount = maxTruckCount;
    }
    /**
     * {@inheritDoc}
     * <br>
     * Равномерная по весу погрузка посылок по машинам
     */
    @Override
    public List<Truck> load(List<Parcel> parcels) {
        List<Truck> trucks = new ArrayList<>();
        for (int i = 0; i < maxTruckCount; i++)
            trucks.add(new Truck());

        parcels.sort(
                Comparator
                        .comparing(Parcel::getHeight)
                        .thenComparing(Parcel::getWidth)
        );
        Collections.reverse(parcels);

        for (Parcel parcel : parcels) {
            boolean isParcelLoaded = false;
            trucks.sort(Comparator.comparingInt(Truck::getWeight));

            for (Truck truck : trucks) {
                if (truck.loadParcel(parcel)) {
                    isParcelLoaded = true;
                    break;
                }
            }

            if (!isParcelLoaded)
                throw new IllegalArgumentException("Невозможно погрузить посылки");
        }

        return trucks;
    }
}
