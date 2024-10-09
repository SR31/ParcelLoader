package ru.liga.parcelloader.util.algorithms;

import ru.liga.parcelloader.type.model.Truck;
import ru.liga.parcelloader.type.model.Parcel;

import java.util.List;

public interface LoadingAlgorithm {
    /**
     * Грузит посылки по машинам
     * @param parcels посылки, которые нужно погрузить
     * @return список машин с погруженными посылками
     */
    List<Truck> load(List<Parcel> parcels);
}
