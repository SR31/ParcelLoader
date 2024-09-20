package ru.liga.parcelloader.repository;

import ru.liga.parcelloader.models.Parcel;

import java.util.List;

public interface ValidFormsOfParcelRepository {
    List<Parcel> getForms();

    Parcel getParcel(int i);
}
