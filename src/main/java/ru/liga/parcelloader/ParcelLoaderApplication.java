package ru.liga.parcelloader;

import ru.liga.menu.model.Menu;
import ru.liga.menu.model.MenuItem;
import ru.liga.parcelloader.algorithms.*;
import ru.liga.parcelloader.counters.DefaultParcelCounter;
import ru.liga.parcelloader.counters.ParcelCounter;
import ru.liga.parcelloader.models.*;
import ru.liga.parcelloader.parsers.*;
import ru.liga.parcelloader.repository.DefaultValidParcelPatterns;
import ru.liga.parcelloader.repository.ValidParcelPatternsRepository;
import ru.liga.parcelloader.validators.ParcelFormValidator;
import ru.liga.parcelloader.writers.TrucksJsonWriter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ParcelLoaderApplication {
    public static void main(String[] args) {
        AtomicReference<LoadingAlgorithm> loadingAlgorithm = new AtomicReference<>();
        AtomicReference<Optional<List<Truck>>> trucks = new AtomicReference<>();
        AtomicReference<Optional<Truck>> truck = new AtomicReference<>(Optional.empty());
        ValidParcelPatternsRepository validParcelPatternsRepository = new DefaultValidParcelPatterns();
        ParcelCounter parcelCounter = new DefaultParcelCounter(validParcelPatternsRepository);

        Menu mainMenu = new Menu("Меню", new Scanner(System.in));
        Menu algorithmPickerMenu = new Menu("Выберите алгоритм для погрузки", new Scanner(System.in));
        Menu printOptionPickerMenu = new Menu("Выберите, что нужно сделать с результатом", new Scanner(System.in));
        Menu truckHandleMethodPickerMenu = new Menu("Вымерите, что нужно сделать с машиной", new Scanner(System.in));

        truckHandleMethodPickerMenu.addMenuItem(
            new MenuItem("Посчитать посылки",
                () -> {
                    truck
                        .get()
                        .ifPresentOrElse(value -> parcelCounter
                                .countParcelsIn(value)
                                .forEach((parcelType, count) -> {
                                    System.out.println(validParcelPatternsRepository.getParcelById(parcelType));
                                    System.out.println("В количестве: " + count + " шт.");
                                }),
                            () -> System.out.println("Машина не была загружена")
                        );
                }
            )
        );

        printOptionPickerMenu.addMenuItem(
            new MenuItem("Вывести на экран",
                () -> trucks
                    .get()
                    .ifPresentOrElse(value -> value
                            .forEach(System.out::println),
                        () -> System.out.println("Машины не были загружены")
                )
            )
        );
        printOptionPickerMenu.addMenuItem(
            new MenuItem("Сохранить в JSON файл", () -> trucks
                .get()
                .ifPresentOrElse(value -> {
                    System.out.print("Введите путь до файла: ");
                    Scanner scanner = new Scanner(System.in);
                    String line = scanner.nextLine();

                    try {
                        new TrucksJsonWriter(value).write(line);
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                },
                    () -> System.out.println("Машины не были загружены")
                )
            )
        );
        printOptionPickerMenu.addMenuItem(
                new MenuItem(
                "Вывести каждую машину на экран с подсчетом типов посылок в ней",
                () -> {
                    AtomicInteger truckNumber = new AtomicInteger(0);
                    trucks
                        .get()
                        .ifPresentOrElse(value -> value
                            .forEach(currentTruck -> {
                                System.out.println("Посылки в машине №" + truckNumber.getAndIncrement() + ":");
                                parcelCounter
                                        .countParcelsIn(currentTruck)
                                        .forEach((parcelType, count) -> {
                                            System.out.println(validParcelPatternsRepository.getParcelById(parcelType));
                                            System.out.println("В количестве: " + count + " шт.");
                                        });
                                System.out.println();
                            }),
                            () -> System.out.println("Машины не были погружены")
                        );
                })
        );

        algorithmPickerMenu.addMenuItem(
            new MenuItem("1 посылка в 1 машину",
                () -> loadingAlgorithm.set(new OneParcelOneTruckAlgorithm())
            )
        );
        algorithmPickerMenu.addMenuItem(
            new MenuItem("Максимальная укомплектовка машин",
                () -> loadingAlgorithm.set(new MaxFillingTrucksAlgorithm())
            )
        );
        algorithmPickerMenu.addMenuItem(
            new MenuItem("Равномерная укомплектовка машин", () -> {
                String line = "";
                Scanner scanner = new Scanner(System.in);
                while(!line.matches("\\d+")) {
                    System.out.print("Введите количество машин: ");
                    line = scanner.nextLine();
                }

                loadingAlgorithm.set(new SameParcelsWeightAlgorithm(Integer.parseInt(line)));
            })
        );

        mainMenu.addMenuItem(
            new MenuItem("Погрузка посылок",  () -> {
                System.out.print("Введите путь до файла с посылками: ");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                FileParser<List<Parcel>> parcelsParser = new ParcelsTxtParser(
                        new ParcelFormValidator(validParcelPatternsRepository)
                );
                List<Parcel> parcels;

                try {
                    parcels = parcelsParser.parse(line);
                } catch (Exception e) {
                    System.out.println("Произошла ошибка: " + e.getMessage());
                    return;
                }

                algorithmPickerMenu.show();
                if (!algorithmPickerMenu.isRunning())
                    return;

                trucks.set(
                        Optional.ofNullable(loadingAlgorithm
                            .get()
                            .load(parcels)
                        )
                );

                do {
                    printOptionPickerMenu.show();
                } while (printOptionPickerMenu.isRunning());
            })
        );
        mainMenu.addMenuItem(
            new MenuItem("Загрузить машину из JSON файла", () -> {
                System.out.print("Введите путь до файла с машиной: ");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();

                try {
                    truck.set(
                            Optional.ofNullable(new TruckJsonParser().parse(line))
                    );
                } catch (Exception e) {
                    System.out.println("При чтении файла произошла ошибка: " + e.getMessage());
                    return;
                }

                System.out.println("Загруженная машина из файла:");
                System.out.println(truck.get().get());

                truckHandleMethodPickerMenu.show();
            })
        );

        do {
            mainMenu.show();
        } while (mainMenu.isRunning());
    }
}