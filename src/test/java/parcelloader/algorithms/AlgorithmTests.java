package parcelloader.algorithms;

import org.junit.jupiter.api.Test;
import ru.liga.parcelloader.util.algorithms.MaxFillingTrucksAlgorithm;
import ru.liga.parcelloader.util.algorithms.OneParcelOneTruckAlgorithm;
import ru.liga.parcelloader.util.algorithms.SameParcelsWeightAlgorithm;
import ru.liga.parcelloader.type.model.Parcel;
import ru.liga.parcelloader.type.model.Truck;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AlgorithmTests {
    @Test
    public void oneParcelToOneTruckAlgorithmShouldLoadNineTrucks() {
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

        List<Truck> trucks = new OneParcelOneTruckAlgorithm().load(parcels);

        assertThat(
                trucks
        ).hasSize(9);

        for (int i = 0; i < 9; i++)
            assertThat(trucks.get(i).getWeight()).isEqualTo(i + 1);
    }

    @Test
    public void maxFillingTrucksAlgorithmShouldLoadFourTrucks() {
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
            add(new Parcel(List.of("666", "666")));
            add(new Parcel(List.of("1")));
            add(new Parcel(List.of("1")));
            add(new Parcel(List.of("777", "7777")));
            add(new Parcel(List.of("8888", "8888")));
            add(new Parcel(List.of("55555")));
            add(new Parcel(List.of("666", "666")));
            add(new Parcel(List.of("777", "7777")));
            add(new Parcel(List.of("999", "999", "999")));
        }};

        List<Truck> trucks = new MaxFillingTrucksAlgorithm().load(parcels);

        assertThat(
                trucks
        ).hasSize(4);

        assertThat(trucks.get(0).getWeight())
                .isEqualTo(trucks.get(0).getCapacity() - 1);
    }

    @Test
    public void sameParcelsWeightAlgorithmShouldLoadTwoTrucksTest() {
        List<Parcel> parcels = new ArrayList<>() {{
            add(new Parcel(List.of("333")));
            add(new Parcel(List.of("22")));
            add(new Parcel(List.of("666", "666")));
        }};

        List<Truck> trucks = new SameParcelsWeightAlgorithm(2).load(parcels);

        assertThat(trucks.get(0).getWeight())
                .isEqualTo(5);

        assertThat(trucks.get(1).getWeight())
                .isEqualTo(6);
    }

    @Test
    public void sameParcelsWeightAlgorithmShouldThrowExceptionTest() {
        List<Parcel> parcels = new ArrayList<>() {{
            add(new Parcel(List.of("8888", "8888")));
            add(new Parcel(List.of("8888", "8888")));
            add(new Parcel(List.of("8888", "8888")));
            add(new Parcel(List.of("8888", "8888")));
            add(new Parcel(List.of("8888", "8888")));
            add(new Parcel(List.of("8888", "8888")));
            add(new Parcel(List.of("8888", "8888")));
            add(new Parcel(List.of("8888", "8888")));
            add(new Parcel(List.of("8888", "8888")));
        }};

        assertThatThrownBy(
                () -> new SameParcelsWeightAlgorithm(2).load(parcels)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
