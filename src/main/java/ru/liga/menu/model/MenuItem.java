package ru.liga.menu.model;

import lombok.Getter;

public class MenuItem {
    @Getter
    private final String title;
    private final Runnable function;

    /**
     * Создает объект, который может быть использован
     * для интерактивного меню
     * @param title название пункта
     * @param function функция (void -> void), которая
     *                 будет выполнена при выборе данного пункта меню
     */
    public MenuItem(String title, Runnable function) {
        this.title = title;
        this.function = function;
    }

    protected void process() {
        function.run();
    }
}
