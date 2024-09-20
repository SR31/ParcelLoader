package ru.liga.parcelloader.parsers;

import ru.liga.parcelloader.models.Parcel;
import ru.liga.parcelloader.validators.ParcelFormValidator;
import ru.liga.parcelloader.validators.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParcelsTxtParser implements FileParser<List<Parcel>> {
    private final Validator<Parcel> parcelValidator;

    public ParcelsTxtParser(Validator<Parcel> parcelValidator) {
        this.parcelValidator = parcelValidator;
    }

    public ParcelsTxtParser() {
        this(new ParcelFormValidator());
    }

    @Override
    public List<Parcel> parse(String filePath) throws IOException {
        List<List<String>> potentialParcels = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(filePath)
        )) {
            String line;
            potentialParcels.add(new ArrayList<>());

            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[\\r\\n]", "");

                if (line.isBlank())
                    potentialParcels.add(new ArrayList<>());
                else
                    potentialParcels.get(potentialParcels.size() - 1).add(line);
            }
        }

        return potentialParcels
                .stream()
                .map(Parcel::new)
                .filter(parcelValidator::isValid)
                .toList();
    }
}
