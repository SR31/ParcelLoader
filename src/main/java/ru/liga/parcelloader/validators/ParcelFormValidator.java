package ru.liga.parcelloader.validators;

import ru.liga.parcelloader.models.Parcel;
import ru.liga.parcelloader.repository.ValidParcelPatternsRepository;

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
