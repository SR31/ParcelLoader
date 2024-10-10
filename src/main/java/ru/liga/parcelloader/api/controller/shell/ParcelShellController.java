package ru.liga.parcelloader.api.controller.shell;

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
import ru.liga.parcelloader.data.parser.ParcelsTxtParser;
import ru.liga.parcelloader.data.parser.StringTrucksParser;
import ru.liga.parcelloader.service.ParcelService;
import ru.liga.parcelloader.service.TruckService;
import ru.liga.parcelloader.type.model.Truck;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("Parcel actions")
@AllArgsConstructor
public class ParcelShellController {
    @Autowired
    private ParcelService parcelService;

    @ShellMethod(key = "pgetall", value = "Просмотреть все доступные посылки")
    public String getAll() {
        return parcelService
                .getAll()
                .stream()
                .map(Parcel::toString)
                .collect(Collectors.joining("\n\n"));
    }

    @ShellMethod(key = "pupdate", value = "Изменить данные посылки")
    public String rename(
            @ShellOption(value = "--id") int id,
            @ShellOption(value = "--name", defaultValue = "") String name,
            @ShellOption(value = "--fillingSymbol", defaultValue = "") String fillingSymbol
    ) {
        if (!Objects.equals(fillingSymbol, "")) {
            if (fillingSymbol.length() != 1) {
                return "fillingSymbol должен быть представлен строкой длины 1";
            }
        }

        try {
            Parcel parcel = parcelService
                    .update(ParcelDTO
                                    .builder()
                                    .name(Objects.equals(name, "") ? null : name)
                                    .fillingSymbol(
                                            Objects.equals(fillingSymbol, "")
                                                    ? null
                                                    : fillingSymbol.charAt(0)
                                    ).build(),
                            id
                    );

            return "Результат изменения:\n" + parcel.toString();
        } catch(Exception e) {
            return "Не удалось обновить данные посылки, проверьте правильность заполнения полей";
        }
    }

    @ShellMethod(key = "pdelete", value = "Удалить посылку")
    public String delete(@ShellOption("--id") int id) {
        parcelService.delete(id);
        return "Готово";
    }
}
