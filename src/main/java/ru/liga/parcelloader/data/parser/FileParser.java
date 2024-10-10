package ru.liga.parcelloader.data.parser;

import java.io.IOException;

public interface FileParser<T> {
    /**
     * Парсит указанный файл
     * @param filePath путь к файлу, который нужно спарсить
     * @return {@link T}, результат парсинга
     * @throws IOException ошибка при чтении файла
     */
    T parse(String filePath) throws IOException;
}
