package ru.liga.parcelloader.algorithms;

import lombok.extern.log4j.Log4j2;
import ru.liga.parcelloader.models.Truck;
import ru.liga.parcelloader.models.Parcel;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class MaxFillingTrucksAlgorithm implements LoadingAlgorithm {
    public List<Truck> run(List<Parcel> parcels) {
        List<Truck> trucks = new ArrayList<>();
        trucks.add(new Truck());

        parcels = sortedByWidthAndHeight(parcels);

        parcelsLoop:
        for (Parcel parcel : parcels) {
            for (Truck truck : trucks) {
                if (truck.loadParcel(parcel)) {
                    continue parcelsLoop;
                }
                log.debug("Parcel doesn't fit in truck\n{}\n{}", parcel, truck);
            }

            Truck newTruck = new Truck();
            newTruck.loadParcel(parcel);
            trucks.add(newTruck);
        }

        return trucks;
    }

    protected List<Parcel> sortedByWidthAndHeight(List<Parcel> parcels) {
        parcels = parcels
                .stream()
                .sorted(
                        Comparator
                                .comparing(Parcel::getHeight)
                                .thenComparing(Parcel::getWidth)
                )
                .collect(Collectors.toCollection(ArrayList::new));

        Collections.reverse(parcels);

        return parcels;
    }
}
