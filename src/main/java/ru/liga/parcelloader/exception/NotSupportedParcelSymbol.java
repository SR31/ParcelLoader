package ru.liga.parcelloader.exception;

public class NotSupportedParcelSymbol extends RuntimeException {
    public NotSupportedParcelSymbol(char symbol) {
        super("Неподдерживаемый символ " + symbol);
    }
}
