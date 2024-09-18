package ru.liga.parcelloader;

import ru.liga.parcelloader.algorithms.ComplexLoadingAlgorithm;
import ru.liga.parcelloader.algorithms.LoadingAlgorithm;
import ru.liga.parcelloader.algorithms.SimpleLoadingAlgorithm;
import ru.liga.parcelloader.parcel.Parcel;
import ru.liga.parcelloader.parcel.ParcelsParser;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String line;
        LoadingAlgorithm loadingAlgorithm;

        while (true) {
            System.out.print("Введите название файла или 0 для выхода: ");

            line = scanner.nextLine();

            if (Objects.equals(line, "0"))
                break;

            ParcelsParser parcelsParser = new ParcelsParser(line);

            try {
                List<Parcel> parcels = parcelsParser.load();

                algType:
                while (true) {
                    System.out.println("Введите режим погрузки или 0 для выхода: ");
                    System.out.println("1. простой");
                    System.out.println("2. усложненный");

                    line = scanner.nextLine();

                    switch (line) {
                        case "0":
                            break algType;
                        case "1":
                            loadingAlgorithm = new SimpleLoadingAlgorithm();
                            break;
                        case "2":
                            loadingAlgorithm = new ComplexLoadingAlgorithm();
                            break;
                        default:
                            System.out.println("Введено неправильное значение");
                            continue;
                    }

                    System.out.println("Результат погрузки посылок:");

                    loadingAlgorithm.run(parcels)
                            .forEach(System.out::println);
                }
            } catch (Exception e) {
                System.out.println("Файл " + line + "не найден");
            }
        }
    }
}