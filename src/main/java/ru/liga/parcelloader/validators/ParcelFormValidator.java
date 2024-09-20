package ru.liga.parcelloader.validators;

import ru.liga.parcelloader.models.Parcel;
import ru.liga.parcelloader.repository.BasicFormsOfParcel;
import ru.liga.parcelloader.repository.ValidFormsOfParcelRepository;

public class ParcelFormValidator implements Validator<Parcel> {
    private final ValidFormsOfParcelRepository validFormsOfParcelRepository;

    public ParcelFormValidator(ValidFormsOfParcelRepository validFormsOfParcelRepository) {
        this.validFormsOfParcelRepository = validFormsOfParcelRepository;
    }

    public ParcelFormValidator() {
        this(new BasicFormsOfParcel());
    }

    @Override
    public boolean isValid(Parcel parcel) {
        return validFormsOfParcelRepository
                .getForms()
                .stream()
                .anyMatch(parcel::equals);
    }
}
