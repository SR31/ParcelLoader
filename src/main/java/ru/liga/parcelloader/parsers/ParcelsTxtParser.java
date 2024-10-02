package ru.liga.parcelloader.parsers;

import ru.liga.parcelloader.models.Parcel;
import ru.liga.parcelloader.models.Truck;
import ru.liga.parcelloader.validators.ParcelFormValidator;
import ru.liga.parcelloader.validators.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParcelsTxtParser implements FileParser<List<Parcel>> {
    private final Validator<Parcel> parcelValidator;

    /**
     *
     * @param parcelValidator репозиторий с правильными формами посылок,
     *                        посылки с неправильными формами будут отброшены при парсинге
     */
    public ParcelsTxtParser(Validator<Parcel> parcelValidator) {
        this.parcelValidator = parcelValidator;
    }

    /**
     *
     * @param filePath путь к файлу, который нужно спарсить
     * @return список посылок
     * @throws IOException ошибка, возникшая при чтении файла
     */
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

                if (line.isBlank()) {
                    potentialParcels.add(new ArrayList<>());
                } else {
                    potentialParcels.get(potentialParcels.size() - 1).add(line);
                }
            }
        }

        return potentialParcels
                .stream()
                .map(Parcel::new)
                .filter(parcelValidator::isValid)
                .collect(Collectors.toList());
    }
}
