package ru.liga.parcelloader.writers;

import java.io.IOException;

public interface FileWriter {
    void write(String filePath) throws IOException;
}
