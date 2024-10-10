package ru.liga.parcelloader.data.parser;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import ru.liga.parcelloader.type.model.Truck;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class TruckJsonParser implements FileParser<Truck> {
    private static final Gson gson = new Gson();

    /**
     *
     * @param filePath путь к файлу, который нужно спарсить
     * @return объект {@link Truck}
     * @throws IOException ошибка, возникшая при чтении файла
     */
    @Override
    public Truck parse(String filePath) throws IOException {
        return gson.fromJson(Files.readString(Path.of(filePath), StandardCharsets.UTF_8), Truck.class);
    }
}
