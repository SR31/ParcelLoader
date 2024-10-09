package ru.liga.parcelloader.data.writer;

import java.io.IOException;

public interface FileWriter {
    void write(String filePath) throws IOException;
}
