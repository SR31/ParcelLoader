import org.junit.jupiter.api.Test;
import ru.liga.parcel.Parcel;
import ru.liga.truck.Truck;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckingThePossibilityOfPlacingParcelTest {
    @Test
    public void allCanBePlaced() {
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

        Truck truck = new Truck(6, 6);

        assertTrue(parcels.stream()
                .allMatch(parcel -> truck.parcelCanBePlaced(parcel, 0, 0))
        );
    }

    @Test
    public void allCannotBePlaced() {
        List<Parcel> parcels = new ArrayList<>() {{
            add(new Parcel(List.of("666", "666")));
            add(new Parcel(List.of("777", "7777")));
            add(new Parcel(List.of("8888", "8888")));
            add(new Parcel(List.of("999", "999", "999")));
        }};

        Truck truck = new Truck(6, 6);
        truck.tryToLoadParcel(parcels.get(1));

        assertTrue(parcels.stream()
                .noneMatch(parcel -> truck.parcelCanBePlaced(parcel, 3, 1))
        );
    }
}
