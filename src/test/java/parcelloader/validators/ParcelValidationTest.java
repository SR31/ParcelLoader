package parcelloader.validators;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.liga.parcelloader.models.Parcel;
import ru.liga.parcelloader.repository.DefaultValidParcelPatterns;
import ru.liga.parcelloader.validators.ParcelFormValidator;
import ru.liga.parcelloader.validators.Validator;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParcelValidationTest {
    public static Validator<Parcel> parcelFormValidator;
    @BeforeAll
    public static void before() {
        parcelFormValidator = new ParcelFormValidator(
                new DefaultValidParcelPatterns()
        );
    }

    @Test
    public void allMustBeValid() {
        List<Parcel> parcels = new ArrayList<>() {{
            add(new Parcel(List.of("1")));
            add(new Parcel(List.of("22")));
            add(new Parcel(List.of("333")));
            add(new Parcel(List.of("4444")));
            add(new Parcel(List.of("55555")));
            add(new Parcel(List.of("666", "666")));
            add(new Parcel(List.of("777", "7777")));
            add(new Parcel(List.of("8888", "8888")));
            add(new Parcel(List.of("999", "999", "999")));
        }};

        assertThat(parcels
                .stream()
                .allMatch(parcel -> parcelFormValidator.isValid(parcel))
        ).isEqualTo(true);
    }

    @Test
    public void allMustBeInvalid() {
        List<Parcel> parcels = new ArrayList<>() {{
            add(new Parcel(List.of("11")));
            add(new Parcel(List.of("22", "22")));
            add(new Parcel(List.of()));
            add(new Parcel(List.of("x")));
        }};

        assertThat(parcels
                .stream()
                .noneMatch(parcel -> parcelFormValidator.isValid(parcel))
        ).isEqualTo(true);;
    }
}