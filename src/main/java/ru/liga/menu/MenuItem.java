package ru.liga.menu;

import lombok.Getter;

public class MenuItem {
    @Getter
    private final String title;
    private final Runnable function;

    public MenuItem(String title, Runnable function) {
        this.title = title;
        this.function = function;
    }

    public void process() {
        function.run();
    }
}
