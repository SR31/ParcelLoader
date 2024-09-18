package ru.liga.parcelloader.truck;

import ru.liga.parcelloader.parcel.Parcel;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Truck {
    private int width = 6;
    private int height = 6;
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

    public boolean parcelCanBePlaced(Parcel parcel, int x, int y) {
        if (y + parcel.getHeight() > space.length)
            return false;

        if (x + parcel.getWidth() > space[0].length)
            return false;

        for (int i = 0; i < parcel.getHeight(); i++) {
            char[] line = parcel.getLine(parcel.getHeight() - i - 1);
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

    public boolean tryToLoadParcel(Parcel parcel) {
        for (int y = 0; y < height - parcel.getHeight() + 1; y++) {
            for (int x = 0; x < width - parcel.getWidth() + 1; x++) {
                if (space[height - y - 1][x] != ' ')
                    continue;

                if (parcelCanBePlaced(parcel, x, y)) {
                    for (int i = 0; i < parcel.getHeight(); i++) {
                        char[] line = parcel.getLine(parcel.getHeight() - i - 1);
                        System.arraycopy(line, 0, space[height - y - i - 1], x, line.length);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    public int getHeight() {
        return space.length;
    }

    public int getWidth() {
        return space[0].length;
    }

    @Override
    public String toString() {
        return "\n" +
                Arrays.stream(space)
                        .map(String::valueOf)
                        .map(line -> "+" + line + "+")
                        .collect(Collectors.joining("\n")) +
                "\n++++++++";
    }
}
