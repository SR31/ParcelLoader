package ru.liga.parcelloader.api.controller.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.liga.parcelloader.api.dto.truck.TruckDTO;
import ru.liga.parcelloader.api.dto.truck.TrucksLoadingDTO;
import ru.liga.parcelloader.service.TruckService;
import ru.liga.parcelloader.type.model.Truck;
import ru.liga.parcelloader.util.algorithms.LoadingAlgorithm;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trucks")
@AllArgsConstructor
@Slf4j
public class TruckController {
    private final TruckService truckService;

    @GetMapping("/load")
    public List<Truck> load(
            @RequestParam("loadingAlgorithm")
            @Pattern(regexp = "(oneToOne|maxFilling|balanced)", message = "Неправильное название алгоритма")
            String loadingAlgorithm,
            @RequestBody TrucksLoadingDTO trucksLoadingDTO
    ) {
        return truckService.load(trucksLoadingDTO, loadingAlgorithm);
    }

    @GetMapping("/countparcels")
    public Map<String, Integer> countParcels(@Valid @RequestBody TruckDTO truckDTO) {
        return truckService.countParcels(truckDTO);
    }
}
