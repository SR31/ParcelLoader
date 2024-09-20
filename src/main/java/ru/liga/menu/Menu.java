package ru.liga.menu;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log4j2
public class Menu {
    Scanner scanner = new Scanner(System.in);
    private final String title;
    private final List<MenuItem> menuItems = new ArrayList<>();
    @Getter
    private boolean running = false;

    public Menu(String title) {
        this.title = title;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void run() {
        log.info("Entered in {} menu", title);

        running = true;

        System.out.println(title);
        System.out.println("[0] Выйти");
        int menuItemPosition = 1;
        for (MenuItem menuItem : menuItems) {
            System.out.println("[" + menuItemPosition + "] " + menuItem.getTitle());
            menuItemPosition++;
        }

        String request = "";
        while(!isRequestCorrect(request)) {
            System.out.print("Выберите пункт меню: ");
            request = scanner.nextLine();
        }

        int requestPosition = Integer.parseInt(request);

        if (requestPosition == 0) {
            running = false;
            return;
        }

        menuItems
                .get(requestPosition - 1)
                .process();
    }

    private boolean isRequestCorrect(String request) {
        if (!request.matches("\\d+"))
            return false;

        int menuPosition = Integer.parseInt(request);

        return menuPosition <= menuItems.size();
    }
}
