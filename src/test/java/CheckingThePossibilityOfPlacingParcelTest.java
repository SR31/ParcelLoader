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
            add(new Parcel(new String[] { "1" }));
            add(new Parcel(new String[] { "22" }));
            add(new Parcel(new String[] { "333" }));
            add(new Parcel(new String[] { "4444" }));
            add(new Parcel(new String[] { "55555" }));
            add(new Parcel(new String[] { "666", "666" }));
            add(new Parcel(new String[] { "777", "7777" }));
            add(new Parcel(new String[] { "8888", "8888" }));
            add(new Parcel(new String[] { "999", "999", "999" }));
        }};

        Truck truck = new Truck(6, 6);

        assertTrue(parcels.stream()
                .allMatch(parcel -> truck.parcelCanBePlaced(parcel, 0, 0))
        );
    }

    @Test
    public void allCannotBePlaced() {
        List<Parcel> parcels = new ArrayList<>() {{
            add(new Parcel(new String[] { "666", "666" }));
            add(new Parcel(new String[] { "777", "7777" }));
            add(new Parcel(new String[] { "8888", "8888" }));
            add(new Parcel(new String[] { "999", "999", "999" }));
        }};

        Truck truck = new Truck(6, 6);
        truck.tryToLoadParcel(parcels.get(1));

        assertTrue(parcels.stream()
                .noneMatch(parcel -> truck.parcelCanBePlaced(parcel, 3, 1))
        );
    }
}
