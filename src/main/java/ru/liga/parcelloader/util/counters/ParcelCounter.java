package ru.liga.parcelloader.util.counters;

import ru.liga.parcelloader.type.model.Truck;

import java.util.Map;

public interface ParcelCounter {
    /**
    * Считает количество посылок каждого типа в грузовике
     * @param truck машина, в которой нужно посчитать посылки
     * @return словарь, который содержит {@link String} ключ - название посылки
     * и {@link Integer} значение - количество посылок с этим идентификатором
     */
    Map<String, Integer> countParcelsIn(Truck truck);
}
