package ru.liga.parcelloader.parsers;

import java.io.IOException;

public interface FileParser<T> {
    T parse(String filePath) throws IOException;
}
