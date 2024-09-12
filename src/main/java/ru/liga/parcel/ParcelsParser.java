package ru.liga.parcel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParcelsParser {
    private final String filePath;

    public ParcelsParser(String filePath) {
        this.filePath = filePath;
    }

    public List<Parcel> load() throws Exception {
        List<Parcel> parcels = new ArrayList<>();
        List<List<String>> parcelParts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/" + filePath))) {
            String line;
            parcelParts.add(new ArrayList<>());

            while ((line = br.readLine()) != null) {
                if (line.replaceAll("[\\r\\n]", "").isBlank()) {
                    parcelParts.add(new ArrayList<>());
                } else {
                    parcelParts.get(parcelParts.size() - 1)
                            .add(line.replaceAll("[\\r\\n]", ""));
                }
            }

            parcelParts.forEach(part -> {
                if (Parcel.isValidFormOfParcel(part))
                    parcels.add(new Parcel(part));
                else {
                    System.out.println("Данная посылка была забракована:");
                    System.out.println("======");
                    part.forEach(System.out::println);
                    System.out.println("======");
                }
            });
        }

        return parcels;
    }
}
