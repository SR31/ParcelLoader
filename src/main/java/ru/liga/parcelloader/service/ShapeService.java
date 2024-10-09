package ru.liga.parcelloader.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.parcelloader.api.dto.parcel.LayerDTO;
import ru.liga.parcelloader.type.model.entity.parcel.Layer;
import ru.liga.parcelloader.type.model.entity.parcel.Shape;
import ru.liga.parcelloader.data.repository.ShapeRepository;

import java.util.List;

@Service
/*
 * Я пока не понял как подружить lombok и MapStruct,
 * поэтому пришлось отказаться
 */
//@AllArgsConstructor(onConstructor = @__(@Autowired))
@AllArgsConstructor
public class ShapeService {
    @Autowired
    private final ShapeRepository shapeRepository;
    @Autowired
    private final LayerService layerService;

    public Shape create(List<LayerDTO> layersDTO) {
        Shape shape = shapeRepository.save(new Shape());
        List<Layer> layers = layerService.save(layersDTO, shape.getId());
        shape.setLayers(layers);

        return shapeRepository.save(shape);
    }

    public List<Shape> findAll() {
        return shapeRepository.findAll();
    }
}