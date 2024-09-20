package ru.liga.parcelloader.counters;

import lombok.extern.log4j.Log4j2;
import ru.liga.parcelloader.models.Parcel;
import ru.liga.parcelloader.models.Truck;
import ru.liga.parcelloader.repository.BasicFormsOfParcel;
import ru.liga.parcelloader.repository.ValidFormsOfParcelRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class ParcelsCounterImpl implements ParcelCounter {
    private final ValidFormsOfParcelRepository validFormsOfParcelRepository;

    public ParcelsCounterImpl(ValidFormsOfParcelRepository validFormsOfParcelRepository) {
        this.validFormsOfParcelRepository = validFormsOfParcelRepository;
    }

    public ParcelsCounterImpl() {
        this(new BasicFormsOfParcel());
    }

    @Override
    public Map<Integer, Integer> countParcelsInTruck(Truck truck) {
        log.debug("Initial truck's state\n{}", truck);

        Map<Integer, Integer> parcelCounts = new HashMap<>();
        char[][] truckSpace = truck.getSpace();

        for (int y = 0; y < truck.getHeight(); y++) {
            for (int x = 0; x < truck.getWidth(); x++) {
                if (truckSpace[truck.getHeight() - y - 1][x] == ' ')
                    continue;

                boolean isParcelPatternFound = false;

                parcelLoop:
                for (int i = 0; i < validFormsOfParcelRepository.getForms().size(); i++) {
                    Parcel parcel = validFormsOfParcelRepository.getParcel(i);
                    if (truck.getWidth() < x + parcel.getWidth())
                        continue;

                    if (truck.getHeight() < y + parcel.getHeight())
                        continue;

                    for (int py = 0; py < parcel.getHeight(); py++) {
                        if (
                                Arrays.compare(
                                        truckSpace[truck.getHeight() - y - py - 1],
                                        x, x + parcel.getLayer(py).length,
                                        parcel.getLayer(py),
                                        0, parcel.getLayer(py).length
                                ) != 0
                        )
                            continue parcelLoop;
                    }

                    isParcelPatternFound = true;

                    parcelCounts.put(
                            i,
                            parcelCounts.getOrDefault(i,  0) + 1
                    );

                    log.debug("Parcel was found at position (x={}, y={})\n{}\n", x, y, parcel);
                    for (int py = 0; py < parcel.getHeight(); py++)
                        Arrays.fill(
                                truckSpace[truck.getHeight() - y - py - 1],
                                x, x + parcel.getLayer(py).length, ' '
                        );
                }

                if (!isParcelPatternFound)
                    throw new IllegalArgumentException(
                            "Неподдерживаемый символ: " +
                                    truckSpace[truck.getHeight() - y - 1][x]
                    );
            }
        }

        return parcelCounts;
    }

    @Override
    public Parcel getParcelById(int id) {
        return validFormsOfParcelRepository.getParcel(id);
    }
}