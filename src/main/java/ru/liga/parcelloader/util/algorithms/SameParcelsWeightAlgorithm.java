package ru.liga.parcelloader.util.algorithms;

import org.springframework.stereotype.Component;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;
import ru.liga.parcelloader.type.model.Truck;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component("balanced")
public class SameParcelsWeightAlgorithm implements LoadingAlgorithm {
    /**
     * {@inheritDoc}
     * <br>
     * Равномерная по весу погрузка посылок по машинам
     */
    @Override
    public List<Truck> load(List<Truck> trucks, List<Parcel> parcels) {
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
