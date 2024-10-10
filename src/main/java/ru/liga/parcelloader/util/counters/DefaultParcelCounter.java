package ru.liga.parcelloader.util.counters;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.parcelloader.data.repository.ParcelRepository;
import ru.liga.parcelloader.type.exception.NotSupportedParcelSymbolException;
import ru.liga.parcelloader.type.model.Truck;
import ru.liga.parcelloader.type.model.entity.parcel.Layer;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;

import java.util.*;

@Log4j2
@Component
@AllArgsConstructor
public class DefaultParcelCounter implements ParcelCounter {
    private static final char EMPTY_SPACE_CHAR = ' ';

    @Autowired
    private final ParcelRepository parcelRepository;

    @Override
    public Map<String, Integer> countParcelsIn(Truck truck) {
        char[][] truckGrid = truck.getGrid();
        Map<String, Integer> parcelCountsMap = new HashMap<>();
        log.debug("Initial truck's state\n{}", truck);
        List<Parcel> potentialParcels = getPotentialParcels(truckGrid);
        potentialParcels.forEach(System.out::println);

        for (int y = 0; y < truck.getHeight(); y++) {
            for (int x = 0; x < truck.getWidth(); x++) {
                char currentChar = truckGrid[truck.getHeight() - y - 1][x];
                if (currentChar == EMPTY_SPACE_CHAR) {
                    continue;
                }

                Parcel parcel = findParcelByFillingSymbol(potentialParcels, currentChar);
                if (parcel == null || !isParcelFormCorrectAt(x, y, parcel, truckGrid)) {
                    throw new NotSupportedParcelSymbolException(currentChar);
                }

                incrementParcelCount(parcelCountsMap, parcel.getName());
                removeParcelInTruckAt(x, y, parcel, truckGrid);
            }
        }

        return parcelCountsMap;
    }

    public boolean isParcelFormCorrectAt(int x, int y, Parcel parcel, char[][] truckGrid) {
        if (truckGrid.length < y + parcel.getHeight()
            || truckGrid[0].length < x + parcel.getWidth()
        ) {
            return false;
        }

        int offset = getParcelOffset(parcel);
        int layersCount = parcel.getShape().getLayers().size();
        int truckHeight = truckGrid.length;
        int truckX = x - offset;

        for (int parcelY = 0; parcelY < layersCount; parcelY++) {
            String layerContent = parcel
                    .getShapeLayer(layersCount - parcelY - 1)
                    .getContent();

            for (int parcelX = 0; parcelX < layerContent.length(); parcelX++) {
                if (layerContent.charAt(parcelX) != EMPTY_SPACE_CHAR) {
                    if (truckGrid[truckHeight - y - parcelY - 1][truckX + parcelX] != parcel.getFillingSymbol()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void removeParcelInTruckAt(int x, int y, Parcel parcel, char[][] truckGrid) {
        int offset = getParcelOffset(parcel);
        int layersCount = parcel.getShape().getLayers().size();
        int truckHeight = truckGrid.length;
        int truckX = x - offset;

        for (int parcelY = 0; parcelY < layersCount; parcelY++) {
            String layerContent = parcel
                    .getShapeLayer(layersCount - parcelY - 1)
                    .getContent();

            for (int parcelX = 0; parcelX < layerContent.length(); parcelX++) {
                if (layerContent.charAt(parcelX) != EMPTY_SPACE_CHAR) {
                    truckGrid[truckHeight - y - parcelY - 1][truckX + parcelX] = EMPTY_SPACE_CHAR;
                }
            }
        }
    }

    private List<Parcel> getPotentialParcels(char[][] truckGrid) {
        Set<Character> uniqueSymbols = new HashSet<>();
        for (char[] chars : truckGrid) {
            for (char symbol : chars) {
                if (symbol != EMPTY_SPACE_CHAR) {
                    uniqueSymbols.add(symbol);
                }
            }
        }

        List<Character> uniqueSymbolsList = uniqueSymbols
                    .stream()
                    .toList();
        return parcelRepository.findAllByFillingSymbolIn(uniqueSymbolsList);
    }

    private Parcel findParcelByFillingSymbol(List<Parcel> potentialParcels, char fillingSymbol) {
        return potentialParcels
                .stream()
                .filter(parcel -> parcel.getFillingSymbol() == fillingSymbol)
                .findFirst()
                .orElse(null);
    }

    private int getParcelOffset(Parcel parcel) {
        int offset = 0;

        int layersCount = parcel.getShape().getLayers().size();
        Layer lastLayer = parcel.getShapeLayer(layersCount - 1);
        String layerContent = lastLayer.getContent();

        for (int i = 0; i < layerContent.length(); i++) {
            if (layerContent.charAt(i) != EMPTY_SPACE_CHAR) {
                break;
            }
            offset++;
        }

        return offset;
    }

    private void incrementParcelCount(Map<String, Integer> parcelCountsMap, String parcelName) {
        int parcelCount = parcelCountsMap.getOrDefault(parcelName, 0);
        parcelCountsMap.put(parcelName, parcelCount + 1);
    }
}