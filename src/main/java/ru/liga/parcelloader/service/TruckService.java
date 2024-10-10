package ru.liga.parcelloader.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.liga.parcelloader.api.dto.parcel.ParcelDTO;
import ru.liga.parcelloader.api.dto.truck.TruckDTO;
import ru.liga.parcelloader.api.dto.truck.TrucksLoadingDTO;
import ru.liga.parcelloader.type.annotation.TrucksGenerator;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;
import ru.liga.parcelloader.type.model.Truck;
import ru.liga.parcelloader.util.algorithms.LoadingAlgorithm;
import ru.liga.parcelloader.util.counters.ParcelCounter;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TruckService {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private final ParcelService parcelService;
    @Autowired
    private final ParcelCounter parcelCounter;

    /**
     * Грузит переданные посылки по грузовикам
     * <br><br>
     * Поле trucks является обязательным для заполнения, если выбран алгоритм balanced
     * @param trucksLoadingDTO объект, представляющий данные о посылках и грузовиках
     * @param loadingAlgorithmName название алгоритма, каким будет осуществляться погрузка
     * @return список грузовиков с посылками
     */
    public List<Truck> load(
            TrucksLoadingDTO trucksLoadingDTO,
            String loadingAlgorithmName
    ) {
         LoadingAlgorithm loadingAlgorithm = context.getBean(
                 loadingAlgorithmName, LoadingAlgorithm.class
         );

         List<Truck> trucks = new ArrayList<>();
         if (!loadingAlgorithm.getClass().isAnnotationPresent(TrucksGenerator.class)) {
             trucks = trucksFromDTO(trucksLoadingDTO.getTrucks());
         }
         List<Parcel> parcels = parcelsFromDTO(trucksLoadingDTO.getParcels());

         return loadingAlgorithm.load(trucks, parcels);
    }

    public Map<String, Integer> countParcels(TruckDTO truckDTO) {
        return parcelCounter.countParcelsIn(new Truck(truckDTO));
    }

    private List<Truck> trucksFromDTO(List<TruckDTO> trucksDTO) {
        return trucksDTO
                .stream()
                .map(truckDTO -> new Truck(truckDTO.getWidth(), truckDTO.getHeight()
                )).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<Parcel> parcelsFromDTO(List<ParcelDTO> parcelsDTO) {
        return parcelsDTO
                .stream()
                .map(parcelDTO -> parcelService.getOne(parcelDTO.getName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
