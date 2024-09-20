package ru.liga.parcelloader.models;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Truck {
    private static final int DEFAULT_WIDTH = 6;
    private static final int DEFAULT_HEIGHT = 6;

    @Getter
    private final int width;
    @Getter
    private final int height;
    @Getter
    private transient int weight = 0;

    private final char[][] space;

    public Truck(int width, int height) {
        this.width = width;
        this.height = height;
        space = new char[height][];

        for (int i = 0; i < height; i++) {
            space[i] = new char[width];
            Arrays.fill(space[i], ' ');
        }
    }

    public Truck(char[][] space) {
        this.space = space;
        this.height = space.length;
        this.width = space[0].length;

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (space[i][j]  != ' ')
                    weight++;
    }

    public Truck() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public int getCapacity() {
        return height * width;
    }

    public char[][] getSpace() {
        char[][] tempSpace = new char[height][width];
        for (int i = 0; i < height; i++)
            System.arraycopy(space[i], 0, tempSpace[i], 0, width);
        return tempSpace;
    }

    public boolean parcelCanBePlaced(Parcel parcel, int x, int y) {
        if (height < y + parcel.getHeight())
            return false;

        if (width < x + parcel.getWidth())
            return false;

        for (int i = 0; i < parcel.getHeight(); i++) {
            char[] line = parcel.getLayer(parcel.getHeight() - i - 1);
            for (int j = 0; j < line.length; j++) {
                if (space[height - y - i - 1][x + j] != ' ')
                    return false;
            }
        }

        if (y == 0)
            return true;

        int supportCount = 0;
        for (int i = 0; i < Math.ceil(parcel.getWidth() / 2.0); i++)
            supportCount += space[height - y][x + i] != ' ' ? 1 : 0;

        return supportCount >= Math.ceil(parcel.getWidth() / 2.0);
    }

    public boolean loadParcel(Parcel parcel) {
        for (int y = 0; y < height - parcel.getHeight() + 1; y++) {
            for (int x = 0; x < width - parcel.getWidth() + 1; x++) {
                if (space[height - y - 1][x] != ' ')
                    continue;

                if (parcelCanBePlaced(parcel, x, y)) {
                    for (int i = 0; i < parcel.getHeight(); i++) {
                        char[] layer = parcel.getLayer(i);
                        System.arraycopy(layer, 0, space[height - y - i - 1], x, layer.length);
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
        return Arrays.stream(space)
                        .map(String::valueOf)
                        .map(line -> "+" + line + "+")
                        .collect(Collectors.joining("\n")) +
                "\n++++++++";
    }
}
