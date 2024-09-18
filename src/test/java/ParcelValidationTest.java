import org.junit.jupiter.api.Test;
import ru.liga.parcelloader.parcel.Parcel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParcelValidationTest {

    @Test
    public void allMustBeValid() {
        List<List<String>> parcels = new ArrayList<>() {{
            add(List.of("1"));
            add(List.of("22"));
            add(List.of("333"));
            add(List.of("4444"));
            add(List.of("55555"));
            add(List.of("666", "666"));
            add(List.of("777", "7777"));
            add(List.of("8888", "8888"));
            add(List.of("999", "999", "999"));
        }};

        assertTrue(parcels.stream().allMatch(Parcel::isValidFormOfParcel));
    }

    @Test
    public void allMustBeInvalid() {
        List<List<String>> parcels = new ArrayList<>() {{
            add(List.of("11"));
            add(List.of("22", "22"));
            add(List.of());
            add(List.of("x"));
        }};

        assertTrue(parcels.stream().noneMatch(Parcel::isValidFormOfParcel));
    }
}