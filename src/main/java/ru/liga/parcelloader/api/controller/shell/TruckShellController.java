package ru.liga.parcelloader.api.controller.shell;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.parcelloader.api.dto.parcel.ParcelDTO;
import ru.liga.parcelloader.api.dto.truck.TruckDTO;
import ru.liga.parcelloader.api.dto.truck.TrucksLoadingDTO;
import ru.liga.parcelloader.data.parser.FileParser;
import ru.liga.parcelloader.data.parser.StringTrucksParser;
import ru.liga.parcelloader.data.parser.TruckJsonParser;
import ru.liga.parcelloader.service.TruckService;
import ru.liga.parcelloader.type.exception.ParcelNotFoundException;
import ru.liga.parcelloader.type.model.Truck;
import ru.liga.parcelloader.util.counters.ParcelCounter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("Truck actions")
@AllArgsConstructor
public class TruckShellController {
    @Autowired
    private final TruckService truckService;
    @Autowired
    private final StringTrucksParser stringTrucksParser;
    @Autowired
    private final FileParser<List<ParcelDTO>> parcelsTxtParser;
    @Autowired
    private final TruckJsonParser truckJsonParser;
    @Autowired
    private final ParcelCounter parcelCounter;

    private List<Truck> trucks;

    @ShellMethod(key = "loadtrucks", value = "Погрузить посылки")
    public String loadTrucks(
            @ShellOption("--algorithm")
            @Pattern(regexp = "(oneToOne|maxFilling|balanced)") String algorithm,
            @ShellOption("--parcelfilepath") String filepath,
            @ShellOption(value = "--trucks", defaultValue = "") String trucks
    ) {
        List<TruckDTO> trucksToLoad = new ArrayList<>();

        if (algorithm.equals("balanced")) {
            try {
                trucksToLoad = stringTrucksParser.parse(trucks);
            } catch (IllegalArgumentException e) {
                return "Произошла ошибка " + e.getMessage();
            }
        }

        List<ParcelDTO> parcels;
        try {
            parcels = parcelsTxtParser.parse(filepath);
        } catch (IOException e) {
            return "Ошибка чтения файла " + filepath;
        } catch (ParcelNotFoundException e) {
            return e.getMessage();
        }

        this.trucks = truckService.load(
                new TrucksLoadingDTO(
                        parcels, trucksToLoad
                ), algorithm);
        return "Готово";
    }

    @ShellMethod(key = "printtrucks", value = "Вывести полученные грузовики на экран")
    public String printTrucks() {
        return trucks
                .stream()
                .map(Truck::toString)
                .collect(Collectors.joining("\n\n"));
    }

    @ShellMethod(key = "countparcels", value = "Посчитать посылки в грузовике")
    public String countTrucks(@ShellOption("--truckfilepath") String filepath) {
        try {
            Truck truck = truckJsonParser.parse(filepath);

            return parcelCounter.countParcelsIn(truck).toString();
        } catch (IOException e) {
            return "Ошибка чтения файла " + filepath;
        }
    }
}
