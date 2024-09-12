package ru.liga.parcel;

import java.util.*;
import java.util.stream.Collectors;

public class Parcel {
    private final char[][] parcel;

    private static final List<String[]> availableParcelModels = new ArrayList<>() {{
        add(new String[] { "1" });
        add(new String[] { "22" });
        add(new String[] { "333" });
        add(new String[] { "4444" });
        add(new String[] { "55555" });
        add(new String[] { "666", "666" });
        add(new String[] { "777", "7777" });
        add(new String[] { "8888", "8888" });
        add(new String[] { "999", "999", "999" });
    }};

    static public boolean isValidFormOfParcel(List<String> parcel) {
        return availableParcelModels.stream().anyMatch(model -> {
            if (model.length != parcel.size())
                return false;

            for (int i = 0; i < model.length; i++) {
                if (!Objects.equals(model[i], parcel.get(i))) return false;
            }

            return true;
        });
    }

    public Parcel(List<String> parcel) {
        int height = parcel.size();
        int width = parcel.get(parcel.size() - 1).length();
        this.parcel = new char[height][width];

        for (int i = 0; i < height; i++) {
            this.parcel[i] = parcel.get(i).toCharArray();
        }
    }

    public int getHeight() {
        return parcel.length;
    }

    public int getWidth() {
        return parcel[parcel.length - 1].length;
    }

    public char[] getLine(int y) {
        return Arrays.copyOf(parcel[y], parcel[y].length);
    }

    public String toString() {
        return "======\n" +
                Arrays.stream(parcel).map(String::valueOf).collect(Collectors.joining("\n")) +
                "\n======";
    }
}
