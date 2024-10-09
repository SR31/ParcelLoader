package ru.liga.parcelloader.api.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.parcelloader.api.dto.parcel.LayerDTO;
import ru.liga.parcelloader.type.model.entity.parcel.Shape;
import ru.liga.parcelloader.service.ShapeService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/shapes")
public class ShapeController {
    private final ShapeService shapeService;

    @GetMapping
    public List<Shape> getAll() {
        return shapeService.findAll();
    }

    @PostMapping
    public Shape create(@RequestBody List<LayerDTO> layers) {
        return shapeService.create(layers);
    }
}
