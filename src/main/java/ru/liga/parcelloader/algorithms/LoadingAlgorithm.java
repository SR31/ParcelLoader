package ru.liga.parcelloader.algorithms;

import ru.liga.parcelloader.models.Truck;
import ru.liga.parcelloader.models.Parcel;

import java.util.List;

public interface LoadingAlgorithm {
    /**
     * Грузит посылки по машинам
     * @param parcels посылки, которые нужно погрузить
     * @return список машин с погруженными посылками
     */
    List<Truck> load(List<Parcel> parcels);
}
