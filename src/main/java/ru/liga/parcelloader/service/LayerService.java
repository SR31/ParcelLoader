package ru.liga.parcelloader.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.parcelloader.api.dto.parcel.LayerDTO;
import ru.liga.parcelloader.type.model.entity.parcel.Layer;
import ru.liga.parcelloader.data.repository.LayerRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class LayerService {
    private final LayerRepository layerRepository;

    public List<Layer> save(List<LayerDTO> layersDTO, int shapeId) {
        List<Layer> layers = layersDTO
                .stream()
                .map(layerDTO -> {
                    layerDTO.setShapeId(shapeId);
                    return new Layer(layerDTO);
                })
                .toList();

        return layerRepository.saveAll(layers);
    }
}
