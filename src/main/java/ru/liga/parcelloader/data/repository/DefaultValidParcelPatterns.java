package ru.liga.parcelloader.data.repository;

import ru.liga.parcelloader.type.model.entity.parcel.Parcel;

import java.util.*;
import java.util.stream.Stream;

public class DefaultValidParcelPatterns implements ValidParcelPatternsRepository {
    private static final Map<String, Parcel> availableParcelForms = new HashMap<>();

    static {
        Stream.of(
                        "1",
                        "22",
                        "333",
                        "4444",
                        "55555",
                        "666\n666",
                        "777\n7777",
                        "8888\n8888",
                        "999\n999\n999"

                )
                .map(element -> element.split("\n"))
                .map(element -> Arrays.stream(element).toList())
                .forEach(element -> availableParcelForms.put(element.get(0).substring(0, 1), new Parcel(element)));
    }

    @Override
    public Map<String, Parcel> getPatterns() {
        return new HashMap<>(availableParcelForms);
    }

    @Override
    public Parcel getParcelById(String id) {
        return availableParcelForms.get(id);
    }
}
