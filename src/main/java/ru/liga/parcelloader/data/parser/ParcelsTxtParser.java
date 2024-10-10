package ru.liga.parcelloader.data.parser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.parcelloader.api.dto.parcel.ParcelDTO;
import ru.liga.parcelloader.service.ParcelService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ParcelsTxtParser implements FileParser<List<ParcelDTO>> {
    private final ParcelService parcelService;

    /**
     *
     * @param filePath путь к файлу, который нужно спарсить
     * @return список посылок
     * @throws IOException ошибка, возникшая при чтении файла
     */
    @Override
    public List<ParcelDTO> parse(String filePath) throws IOException {
        List<ParcelDTO> parcels = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(filePath)
        )) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[\\r\\n]", "");

                if (!line.isBlank()) {
                    parcels.add(ParcelDTO
                            .builder()
                            .name(line)
                            .build()
                    );
                }
            }
        }

        return parcels;
    }
}
