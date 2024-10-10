package ru.liga.parcelloader.type.validator;

import ru.liga.parcelloader.data.repository.ValidParcelPatternsRepository;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;

public class ParcelFormValidator implements Validator<Parcel> {
    private final ValidParcelPatternsRepository validParcelPatternsRepository;

    public ParcelFormValidator(ValidParcelPatternsRepository validParcelPatternsRepository) {
        this.validParcelPatternsRepository = validParcelPatternsRepository;
    }

    @Override
    public boolean isValid(Parcel parcel) {
        return validParcelPatternsRepository
                .getPatterns()
                .values()
                .stream()
                .anyMatch(parcel::equals);
    }
}
