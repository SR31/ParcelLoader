package ru.liga.parcelloader.counters;

import lombok.extern.log4j.Log4j2;
import ru.liga.parcelloader.exception.NotSupportedParcelSymbol;
import ru.liga.parcelloader.models.Parcel;
import ru.liga.parcelloader.models.Truck;
import ru.liga.parcelloader.repository.ValidParcelPatternsRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Log4j2
public class DefaultParcelCounter implements ParcelCounter {
    private static final char EMPTY_SPACE_CHAR = ' ';

    private final Map<String, Integer> parcelCountsMap;
    private final ValidParcelPatternsRepository validParcelPatternsRepository;
    private final Set<Map.Entry<String, Parcel>> validParcelForms;
    private char[][] truckGrid;

    /**
     * Создает экземпляр класса для подсчета посылок в машине
     * @param validParcelPatternsRepository репозиторий с правильными формами посылок
     * @param parcelCountsMap объект Map, куда будут записан результат
     */
    public DefaultParcelCounter(ValidParcelPatternsRepository validParcelPatternsRepository,
                                Map<String, Integer> parcelCountsMap) {
        this.parcelCountsMap = parcelCountsMap;
        this.validParcelPatternsRepository = validParcelPatternsRepository;
        this.validParcelForms = validParcelPatternsRepository.getPatterns().entrySet();
    }

    @Override
    public Map<String, Integer> countParcelsIn(Truck truck) {
        log.debug("Initial truck's state\n{}", truck);
        truckGrid = truck.getGrid();

        for (int y = 0; y < truck.getHeight(); y++) {
            for (int x = 0; x < truck.getWidth(); x++) {
                char currentChar = truckGrid[truck.getHeight() - y - 1][x];

                if (currentChar == EMPTY_SPACE_CHAR) {
                    continue;
                }

                Optional<String> parcelPatternId = findParcelPatternIdAt(x, y);

                if (parcelPatternId.isEmpty()) {
                    throw new NotSupportedParcelSymbol(currentChar);
                }

                int parcelCount = parcelCountsMap.getOrDefault(parcelPatternId.get(), 0);
                parcelCountsMap.put(parcelPatternId.get(), parcelCount + 1);
                removeParcelInTruckAt(x, y, validParcelPatternsRepository.getParcelById(parcelPatternId.get()));
            }
        }

        return parcelCountsMap;
    }

    private Optional<String> findParcelPatternIdAt(int x, int y) {
        return validParcelForms
                .stream()
                .filter(entry -> isTruckContainParcelAt(x, y, entry.getValue()))
                .findFirst()
                .map(Map.Entry::getKey);
    }

    private boolean isTruckContainParcelAt(int x, int y, Parcel parcel) {
        if (truckGrid.length < y + parcel.getHeight()
                || truckGrid[0].length < x + parcel.getWidth())
            return false;

        for (int parcelLayer = 0; parcelLayer < parcel.getHeight(); parcelLayer++) {
            if (
                    Arrays.compare(
                            truckGrid[truckGrid.length - y - parcelLayer - 1],
                            x, x + parcel.getLayer(parcelLayer).length,
                            parcel.getLayer(parcelLayer),
                            0, parcel.getLayer(parcelLayer).length
                    ) != 0
            ) {
                return false;
            }
        }

        return true;
    }

    private void removeParcelInTruckAt(int fromX, int fromY, Parcel parcel) {
        for (int parcelLayer = 0; parcelLayer < parcel.getHeight(); parcelLayer++) {
            Arrays.fill(
                    truckGrid[truckGrid.length - fromY - parcelLayer - 1],
                    fromX, fromX + parcel.getLayer(parcelLayer).length, ' '
            );
        }
    }
}