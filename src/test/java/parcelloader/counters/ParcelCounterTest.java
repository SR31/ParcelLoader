package parcelloader.counters;

import org.junit.jupiter.api.Test;
import ru.liga.parcelloader.counters.DefaultParcelCounter;
import ru.liga.parcelloader.models.Truck;
import ru.liga.parcelloader.repository.DefaultValidParcelPatterns;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ParcelCounterTest {
    @Test
    public void parcelCounterTest() {
        Truck truck = new Truck(new char[][]{
                { ' ', ' ', ' ', ' ', ' ', '1' },
                { ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', '7', '7', '7', '1' },
                { ' ', ' ', '7', '7', '7', '7' },
                { '1', ' ', ' ', '6', '6', '6' },
                { '2', '2', '1', '6', '6', '6' }
        });

        Map<String, Integer> parcelsCount = new DefaultParcelCounter(
                new DefaultValidParcelPatterns(), new HashMap<>()
        ).countParcelsIn(truck);

        assertThat(parcelsCount).isEqualTo(
                new HashMap<String, Integer>() {{
                    put("1", 4);
                    put("2", 1);
                    put("6", 1);
                    put("7", 1);
                }}
        );
    }
}
