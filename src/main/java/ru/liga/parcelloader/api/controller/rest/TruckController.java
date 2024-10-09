package ru.liga.parcelloader.api.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.parcelloader.api.dto.truck.TruckDTO;
import ru.liga.parcelloader.api.dto.truck.TrucksLoadingDTO;
import ru.liga.parcelloader.service.TruckService;
import ru.liga.parcelloader.type.model.Truck;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trucks")
@AllArgsConstructor
public class TruckController {
    private final TruckService truckService;

    @GetMapping("/load")
    public List<Truck> load(
            @RequestParam("loadingAlgorithm") String loadingAlgorithm,
            @RequestBody TrucksLoadingDTO trucksLoadingDTO
    ) {
        return truckService.load(trucksLoadingDTO, loadingAlgorithm);
    }

    @GetMapping("/countparcels")
    public Map<String, Integer> countParcels(@RequestBody TruckDTO truckDTO) {
        return truckService.countParcels(truckDTO);
    }
}
