package ru.liga.parcelloader.type.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

import ru.liga.parcelloader.api.dto.truck.TruckDTO;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;
import ru.liga.parcelloader.type.model.entity.parcel.Shape;

public class Truck {
    private static final int DEFAULT_WIDTH = 6;
    private static final int DEFAULT_HEIGHT = 6;

    @Getter
    private final int width;
    @Getter
    private final int height;
    @Getter
    private transient int weight = 0;
    private final char[][] grid;

    /**
     *
     * @param width ширина машины
     * @param height высота машины
     */
    public Truck(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new char[height][];

        for (int i = 0; i < height; i++) {
            grid[i] = new char[width];
            Arrays.fill(grid[i], ' ');
        }
    }

    /**
     * Автоматически создает грузовик по его содержимому
     *
     * @param grid двумерный массив элементов {@link Character}, представляющих
     *             содержимое машины
     */
    public Truck(char[][] grid) {
        this.grid = grid;
        this.height = grid.length;
        this.width = grid[0].length;

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (grid[i][j]  != ' ')
                    weight++;
    }

    /**
     * Создание машины стандартного размера (6х6)
     */
    public Truck() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Truck(TruckDTO truckDTO) {
        if (truckDTO.getGrid() != null) {
            this.grid = truckDTO.getGrid();
            this.height = this.grid.length;
            this.width = this.grid[0].length;
        } else {
            this.grid = new char[truckDTO.getHeight()][truckDTO.getWidth()];
            for (int i = 0; i < truckDTO.getHeight(); i++)
                for (int j = 0; j < truckDTO.getWidth(); j++)
                    this.grid[i][j] = ' ';
            this.height = truckDTO.getHeight();
            this.width = truckDTO.getWidth();
        }
    }

    /**
     * Возвращает копию содержимого машины
     * @return двумерный массив элементов {@link Character}
     */
    public char[][] getGrid() {
        return Arrays.copyOf(grid, grid.length);
    }

    /**
     * Проверяет, может ли указанная посылка быть погружена в машину
     * @param parcel посылка для погрузки
     * @param x координата x
     * @param y координата y
     * @return true, если может, и false, если не может
     */
    public boolean parcelCanBePlaced(Parcel parcel, int x, int y) {
        if (height < y + parcel.getHeight()
            || width < x + parcel.getWidth()) {
            return false;
        }

        Shape shape = parcel.getShape();

        for (int i = 0; i < parcel.getHeight(); i++) {
            char[] line = shape.getLayers().get(parcel.getHeight() - i - 1).getContent().toCharArray();
            for (int j = 0; j < line.length; j++) {
                if (grid[height - y - i - 1][x + j] != ' ') {
                    return false;
                }
            }
        }

        if (y == 0) {
            return true;
        }

        int supportCount = 0;
        for (int i = 0; i < Math.ceil(parcel.getWidth() / 2.0); i++) {
            supportCount += grid[height - y][x + i] != ' ' ? 1 : 0;
        }

        return supportCount >= Math.ceil(parcel.getWidth() / 2.0);
    }

    /**
     * Грузит указанную посылку в машину (в первое доступное место), если такое возможно
     * @param parcel посылка для погрузки
     * @return true, если посылка была погружена, и false, если не удалось погрузить
     */
    public boolean loadParcel(Parcel parcel) {
        Shape shape = parcel.getShape();

        for (int y = 0; y < height - parcel.getHeight() + 1; y++) {
            for (int x = 0; x < width - parcel.getWidth() + 1; x++) {
                if (grid[height - y - 1][x] != ' ') {
                    continue;
                }

                if (parcelCanBePlaced(parcel, x, y)) {
                    for (int i = 0; i < parcel.getHeight(); i++) {
                        char[] layer = shape.getLayers().get(parcel.getHeight() - i - 1).getContent().toCharArray();
                        System.arraycopy(layer, 0, grid[height - y - i - 1], x, layer.length);
                    }
                    weight += parcel.getWeight();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return Arrays.stream(grid)
                        .map(String::valueOf)
                        .map(line -> "+" + line + "+")
                        .collect(Collectors.joining("\n")) +
                "\n" + "+".repeat(width + 2);
    }
}
