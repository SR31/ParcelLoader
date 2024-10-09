package ru.liga.parcelloader.type.model;

import java.util.*;
import java.util.stream.Collectors;

public class Parcel {
    private final char[][] body;

    public Parcel(List<String> parcel) {
        int height = parcel.size();
        int width = parcel.isEmpty() ? 0 : parcel.get(parcel.size() - 1).length();
        this.body = new char[height][width];

        for (int i = 0; i < height; i++) {
            this.body[i] = parcel.get(i).toCharArray();
        }
    }

    public int getHeight() {
        return body.length;
    }

    public int getWidth() {
        return body[body.length - 1].length;
    }

    public char[] getLayer(int y) {
        return Arrays.copyOf(body[getHeight() - y - 1], body[getHeight() - y - 1].length);
    }

    public int getWeight() {
        return Arrays.stream(body)
                .map(layer -> layer.length)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public boolean equals(Parcel parcel) {
        return Arrays.deepEquals(body, parcel.body);
    }

    @Override
    public String toString() {
        return "======\n" +
                Arrays.stream(body)
                        .map(String::valueOf)
                        .collect(Collectors.joining("\n")) +
                "\n======";
    }
}
