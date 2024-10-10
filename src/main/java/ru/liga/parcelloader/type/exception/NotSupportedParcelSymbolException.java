package ru.liga.parcelloader.type.exception;

public class NotSupportedParcelSymbolException extends RuntimeException {
    public NotSupportedParcelSymbolException(char symbol) {
        super("Неподдерживаемый символ " + symbol);
    }
}
