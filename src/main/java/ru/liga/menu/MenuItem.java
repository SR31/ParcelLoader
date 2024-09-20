package ru.liga.menu;

import lombok.Getter;

public class MenuItem {
    @Getter
    private final String title;
    private final Procedure function;

    public MenuItem(String title, Procedure function) {
        this.title = title;
        this.function = function;
    }

    public void process() {
        function.execute();
    }

}
