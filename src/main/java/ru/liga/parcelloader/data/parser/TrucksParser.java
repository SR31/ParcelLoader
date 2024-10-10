package ru.liga.parcelloader.data.parser;

import ru.liga.parcelloader.api.dto.truck.TruckDTO;

import java.util.List;

public interface TrucksParser<T> {
    List<TruckDTO> parse(T data) throws IllegalArgumentException;
}
