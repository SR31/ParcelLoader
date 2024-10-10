package ru.liga.parcelloader.data.parser;

import org.springframework.stereotype.Component;
import ru.liga.parcelloader.api.dto.truck.TruckDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StringTrucksParser implements TrucksParser<String> {
    public List<TruckDTO> parse(String trucksString) throws IllegalArgumentException {
        List<String> potentialTrucks = Arrays.stream(trucksString.split("(,+)"))
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println(potentialTrucks);
        System.out.println(potentialTrucks.size());

        List<TruckDTO> trucks = new ArrayList<>();
        potentialTrucks.forEach(potentialTruck -> {
            if (!potentialTruck.matches("[1-9]\\d*x[1-9]\\d*")) {
                throw new IllegalArgumentException("Невалидная форма грузовика: " + potentialTruck);
            }

            int[] parts = Arrays.stream(potentialTruck.split("x")).mapToInt(Integer::parseInt).toArray();
            TruckDTO truckDTO = new TruckDTO();
            truckDTO.setWidth(parts[0]);
            truckDTO.setHeight(parts[1]);
            trucks.add(truckDTO);
        });

        return trucks;
    }
}
