package ru.liga.parcelloader.util.algorithms;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.liga.parcelloader.type.annotation.TrucksGenerator;
import ru.liga.parcelloader.type.model.Truck;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;

import java.util.*;

@Log4j2
@Component("maxFilling")
@TrucksGenerator
public class MaxFillingTrucksAlgorithm implements LoadingAlgorithm {
    /**
     * {@inheritDoc}
     * <br>
     * Максимальная укомплектовка машин
     * Машин при погрузке может быть неограниченное количество
     */
    public List<Truck> load(List<Truck> trucks, List<Parcel> parcels) {
        trucks.clear();
        trucks.add(new Truck());

        parcels.sort(
                Comparator
                    .comparing(Parcel::getHeight)
                    .thenComparing(Parcel::getWidth)
        );
        Collections.reverse(parcels);

        for (Parcel parcel : parcels) {
            boolean isParcelLoaded = false;

            for (Truck truck : trucks) {
                if (truck.loadParcel(parcel)) {
                    isParcelLoaded = true;
                    break;
                }

                log.debug("Parcel doesn't fit in truck\n{}\n{}", parcel, truck);
            }

            if (isParcelLoaded)
                continue;

            Truck newTruck = new Truck();
            newTruck.loadParcel(parcel);
            trucks.add(newTruck);
        }

        return trucks;
    }
}
