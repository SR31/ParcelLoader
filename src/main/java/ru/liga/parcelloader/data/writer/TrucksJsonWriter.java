package ru.liga.parcelloader.data.writer;

import com.google.gson.Gson;
import ru.liga.parcelloader.type.model.Truck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class TrucksJsonWriter implements FileWriter {
    private final List<Truck> trucks;
    private static final Gson gson = new Gson();

    public TrucksJsonWriter(List<Truck> trucks) {
        this.trucks = trucks;
    }
    @Override
    public void write(String filePath) throws IOException {
        String jsonString = convertTrucksToJson();
        Files.writeString(
                Path.of(filePath),
                jsonString,
                StandardOpenOption.CREATE
        );
    }

    private String convertTrucksToJson() {
        return gson.toJson(trucks);
    }
}
