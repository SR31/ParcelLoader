package ru.liga.parcelloader.api.controller.shell;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.parcelloader.api.dto.parcel.ParcelDTO;
import ru.liga.parcelloader.service.ParcelService;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;

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
            @ShellOption(value = "--fillingSymbol", defaultValue = "")
            @Size(max = 1)
            String fillingSymbol
    ) {
        try {
            Parcel parcel = parcelService.update(
                    parcelDTOFromParameters(name, fillingSymbol), id
            );

            return "Результат изменения:\n" + parcel.toString();
        } catch(RuntimeException e) {
            return "Не удалось обновить данные посылки: " + e.getMessage();
        }
    }

    @ShellMethod(key = "pdelete", value = "Удалить посылку")
    public String delete(@ShellOption("--id") int id) {
        parcelService.delete(id);
        return "Готово";
    }

    private ParcelDTO parcelDTOFromParameters(String name, String fillingSymbol) {
        return ParcelDTO
                .builder()
                .name(Objects.equals(name, "") ? null : name)
                .fillingSymbol(
                        Objects.equals(fillingSymbol, "")
                                ? null
                                : fillingSymbol.charAt(0)
                ).build();
    }
}
