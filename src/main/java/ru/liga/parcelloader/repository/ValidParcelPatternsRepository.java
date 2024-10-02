package ru.liga.parcelloader.repository;

import ru.liga.parcelloader.models.Parcel;

import java.util.List;
import java.util.Map;

public interface ValidParcelPatternsRepository {
    Map<String, Parcel> getPatterns();

    Parcel getParcelById(String id);
}
