package ru.liga.parcelloader.counters;

import ru.liga.parcelloader.models.Truck;

import java.util.Map;

public interface ParcelCounter {
    /**
     * Агрегирует посылки в машине по типу и суммирует их количество
     * @param truck машина, в которой нужно посчитать посылки
     * @return словарь, который содержит {@link String} ключ - идентификатор посылки
     * и {@link Integer} значение - количество посылок с этим идентификатором
     */
    Map<String, Integer> countParcelsIn(Truck truck);
}
