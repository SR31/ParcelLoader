import org.junit.jupiter.api.Test;
import ru.liga.parcelloader.counters.ParcelCounter;
import ru.liga.parcelloader.counters.ParcelsCounterImpl;
import ru.liga.parcelloader.models.Truck;
import ru.liga.parcelloader.repository.BasicFormsOfParcel;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ParcelCounterTest {
    @Test
    public void parcelCounterTest() {
        ParcelCounter parcelCounter = new ParcelsCounterImpl(
                new BasicFormsOfParcel()
        );

        Truck truck = new Truck(new char[][]{
                { ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', '7', '7', '7', '1' },
                { ' ', ' ', '7', '7', '7', '7' },
                { '1', ' ', ' ', '6', '6', '6' },
                { '2', '2', '1', '6', '6', '6' }
        });

        Map<Integer, Integer> parcelsCount = new ParcelsCounterImpl().countParcelsInTruck(truck);

        assertThat(parcelsCount).isEqualTo(
                new HashMap<Integer, Integer>() {{
                    put(0, 3);
                    put(1, 1);
                    put(5, 1);
                    put(6, 1);
                }}
        );
    }
}
