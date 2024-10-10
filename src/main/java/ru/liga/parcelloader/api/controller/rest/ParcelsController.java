package ru.liga.parcelloader.api.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.parcelloader.api.dto.parcel.ParcelDTO;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;
import ru.liga.parcelloader.service.ParcelService;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/parcels")
public class ParcelsController {
    private final ParcelService parcelService;

    @GetMapping
    public List<Parcel> getAll() {
        return parcelService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Parcel> getOne(@PathVariable("id") int id) {
        return parcelService.getOne(id);
    }

    @PostMapping
    public Parcel create(@RequestBody ParcelDTO parcel) {
        return parcelService.create(parcel);
    }

    @PatchMapping("/{id}")
    public Parcel update(@PathVariable("id") int id, @RequestBody ParcelDTO parcel) {
        return parcelService.update(parcel, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        parcelService.delete(id);
    }
}
