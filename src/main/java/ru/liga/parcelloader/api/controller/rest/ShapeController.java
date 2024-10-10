package ru.liga.parcelloader.api.controller.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.liga.parcelloader.api.dto.parcel.LayerDTO;
import ru.liga.parcelloader.api.dto.parcel.ShapeDTO;
import ru.liga.parcelloader.api.validator.ShapeMustBePresentedBySameSymbols;
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
    public Shape create(
            @Valid
            @RequestBody
            ShapeDTO shapeDTO
    ) {
        return shapeService.create(shapeDTO.getLayers());
    }
}
