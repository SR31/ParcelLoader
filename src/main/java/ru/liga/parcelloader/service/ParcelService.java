package ru.liga.parcelloader.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.parcelloader.api.dto.parcel.ParcelDTO;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;
import ru.liga.parcelloader.data.repository.ParcelRepository;
import ru.liga.parcelloader.type.model.entity.parcel.Shape;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParcelService {
    private final ParcelRepository parcelRepository;

    public List<Parcel> getAll() {
        return parcelRepository.findAll();
    }

    public Optional<Parcel> getOne(int id) {
        return parcelRepository.findById(id);
    }

    public Parcel create(ParcelDTO parcelDTO) {
        return parcelRepository.save(new Parcel(parcelDTO));
    }

    public Parcel update(ParcelDTO parcelDTO, int id) {
        Optional<Parcel> optionalParcel = parcelRepository.findById(id);

        if (optionalParcel.isEmpty()) {
            return create(parcelDTO);
        }

        Parcel parcel = optionalParcel.get();
        if (parcelDTO.getName() != null) {
            parcel.setName(parcelDTO.getName());
        }
        if (parcelDTO.getFillingSymbol() != null) {
            parcel.setFillingSymbol(parcelDTO.getFillingSymbol());
        }
        if (parcelDTO.getShapeId() != null) {
            parcel.setShape(Shape
                    .builder()
                    .id(parcelDTO.getShapeId())
                    .build()
            );
        }

        return parcelRepository.save(parcel);
    }
}
