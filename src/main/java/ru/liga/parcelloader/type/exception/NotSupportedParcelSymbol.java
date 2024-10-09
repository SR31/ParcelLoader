package ru.liga.parcelloader.type.exception;

public class NotSupportedParcelSymbol extends RuntimeException {
    public NotSupportedParcelSymbol(char symbol) {
        super("Неподдерживаемый символ " + symbol);
    }
}
