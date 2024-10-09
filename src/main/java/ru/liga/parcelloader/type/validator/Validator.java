package ru.liga.parcelloader.type.validator;

public interface Validator<T> {
    /**
     * Проверяет, удовлетворяет ли некоторым правилам
     * переданный объект
     * @param object объект, который нужно проверить
     * @return true, если удовлетворяет, и false, если не удовлетворяет
     */
    boolean isValid(T object);
}
