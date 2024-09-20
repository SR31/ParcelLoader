package ru.liga.parcelloader.parsers;

import com.google.gson.Gson;
import ru.liga.parcelloader.models.Truck;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TruckJsonParser implements FileParser<Truck> {
    private static final Gson gson = new Gson();

    @Override
    public Truck parse(String filePath) throws IOException {
        return gson.fromJson(Files.readString(Path.of(filePath), StandardCharsets.UTF_8), Truck.class);
    }
}
