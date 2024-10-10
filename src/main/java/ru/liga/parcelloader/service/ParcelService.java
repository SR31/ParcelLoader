package ru.liga.parcelloader.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.parcelloader.api.dto.parcel.ParcelDTO;
import ru.liga.parcelloader.data.mapper.ParcelMapper;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;
import ru.liga.parcelloader.data.repository.ParcelRepository;

import java.util.List;
import java.util.Optional;

@Service
/*
 * Я пока не понял как подружить lombok и MapStruct,
 * поэтому пришлось отказаться
 */
//@AllArgsConstructor(onConstructor = @__(@Autowired))
@AllArgsConstructor
public class ParcelService {
    @Autowired
    private final ParcelRepository parcelRepository;
    @Autowired
    private final ParcelMapper parcelMapper;

    public List<Parcel> getAll() {
        return parcelRepository.findAll();
    }

    public Optional<Parcel> getOne(int id) {
        return parcelRepository.findById(id);
    }

    public Optional<Parcel> getOne(String name) {
        return parcelRepository.findByName(name);
    }

    public Parcel create(ParcelDTO parcelDTO) {
        return parcelRepository.save(new Parcel(parcelDTO));
    }

    /**
     * Модифицирует объект {@link Parcel}.
     * <br>
     * Если объекта с переданным id не существует, то создает новый
     * с переданными полями.
     * Если объект существует, то результатом изменения будет
     * слияние существующего объекта и переданного
     * (доступно изменение конкретных полей, а не всего объекта).
     *
     * @param parcelDTO объект, содержащий поля с новыми данными
     * @param id идентификатор объекта для изменения
     * @return результирующий объект {@link Parcel}
     */
    public Parcel update(ParcelDTO parcelDTO, int id) {
        Optional<Parcel> optionalParcel = parcelRepository.findById(id);

        if (optionalParcel.isEmpty()) {
            return create(parcelDTO);
        }

        Parcel parcel = optionalParcel.get();
        parcelMapper.updateParcelFromDTO(parcel, parcelDTO);

        return parcelRepository.save(parcel);
    }

    public void delete(int id) {
        parcelRepository.deleteById(id);
    }
}
