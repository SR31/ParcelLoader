package ru.liga.parcelloader.data.repository;

import ru.liga.parcelloader.type.model.entity.parcel.Parcel;

import java.util.Map;

public interface ValidParcelPatternsRepository {
    Map<String, Parcel> getPatterns();

    Parcel getParcelById(String id);
}
