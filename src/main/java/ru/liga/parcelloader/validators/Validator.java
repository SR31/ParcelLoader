package ru.liga.parcelloader.validators;

public interface Validator<T> {
    boolean isValid(T object);
}
