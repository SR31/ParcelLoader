package ru.liga.parcelloader.repository;

import ru.liga.parcelloader.models.Parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BasicFormsOfParcel implements ValidFormsOfParcelRepository {
    private final List<Parcel> availableParcelForms = new ArrayList<>();

    public BasicFormsOfParcel() {
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
                .forEach(element -> availableParcelForms.add(new Parcel(element)));
    }

    @Override
    public List<Parcel> getForms() {
        return availableParcelForms;
    }
    
    @Override
    public Parcel getParcel(int i) {
        return availableParcelForms.get(i);
    }
}
