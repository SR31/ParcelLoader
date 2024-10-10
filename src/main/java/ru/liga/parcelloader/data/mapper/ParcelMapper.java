package ru.liga.parcelloader.data.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.liga.parcelloader.api.dto.parcel.ParcelDTO;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;

@Mapper(componentModel = "spring")
public interface ParcelMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateParcelFromDTO(
            @MappingTarget Parcel parcel,
            ParcelDTO parcelDTO
    );
}
