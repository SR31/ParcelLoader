package ru.liga.menu.model;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log4j2
public class Menu {
    private final Scanner scanner;
    private final String title;
    private final List<MenuItem> menuItems = new ArrayList<>();
    @Getter
    private boolean running = false;

    /**
     * Создает новый объект меню
     * @param title название меню, будет показано в самом начале
     * @param scanner {@link Scanner} экземпляр, который будет считывать пользовательские строки
     */
    public Menu(String title, Scanner scanner) {
        this.title = title;
        this.scanner = scanner;
    }

    /**
     * Добавляет новый пункт меню
     * @param menuItem пункт меню, экземпляр {@link MenuItem}
     */
    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void show() {
        print();
        int menuIndex = requestMenuItemIndex();

        if (menuIndex == -1) {
            running = false;
            return;
        }

        menuItems
                .get(menuIndex)
                .process();
    }

    private void print() {
        log.info("Entered in {} menu", title);
        running = true;

        System.out.println(title);
        System.out.println("[0] Выйти");
        int menuItemPosition = 1;
        for (MenuItem menuItem : menuItems) {
            System.out.println("[" + menuItemPosition + "] " + menuItem.getTitle());
            menuItemPosition++;
        }
    }

    private int requestMenuItemIndex() {
        String line = "";
        while(!isRequestCorrect(line)) {
            System.out.print("Выберите пункт меню: ");
            line = scanner.nextLine();
        }

        return Integer.parseInt(line) - 1;
    }

    private boolean isRequestCorrect(String request) {
        if (!request.matches("\\d+"))
            return false;

        int menuPosition = Integer.parseInt(request);

        return menuPosition <= menuItems.size();
    }
}
