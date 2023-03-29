package stacs.estate.cs5031p3code.menu;

import org.springframework.web.reactive.function.client.WebClientException;
import stacs.estate.cs5031p3code.client.TerminalClient;

import java.util.Scanner;

/**
 * An implementation of the {@link Menu}.
 * This is the main menu of the system.
 *
 * @author 190005675
 */
public class MainMenu implements Menu {
    private final Scanner scanner;
    private final TerminalClient client;
    private final String userKey;
    private static final String DISPLAY = """
            \nMAIN MENU
            [0] USER MENU
            [1] BUILDING MENU
            [2] FLAT MENU
            [3] ROLE MENU
            [q] logout
            """;

    public MainMenu(Scanner scanner, TerminalClient client, String userKey) {
        this.scanner = scanner;
        this.client = client;
        this.userKey = userKey;
    }

    @Override
    public void run() {
        String response;
        System.out.println(DISPLAY);
        while (scanner.hasNextLine()) {
            try {
                String input = scanner.nextLine();
                switch (input) {
                    case "q" -> {
                        response = client.logout(userKey);
                        System.out.println(response);
                        return;
                    }
                    case "0" -> {
                        Menu userMenu = new UserMenu(scanner, client, userKey);
                        userMenu.run();
                        response = null;
                    }
                    case "1" -> {
                        Menu buildingMenu = new BuildingMenu(scanner, client, userKey);
                        buildingMenu.run();
                        response = null;
                    }
                    case "2" -> {
                        Menu flatMenu = new FlatMenu(scanner, client, userKey);
                        flatMenu.run();
                        response = null;
                    }
                    case "3" -> {
                        Menu roleMenu = new RoleMenu(scanner, client, userKey);
                        roleMenu.run();
                        response = null;
                    }
                    default -> {
                        response = "[Invalid] Unsupported command";
                    }
                }
            } catch (WebClientException e) {
                response = "[Error] Connection Failure: Please make sure the server is running";
            }
            if (response != null) {
                System.out.println(response);
            }
            System.out.println(DISPLAY);
        }
    }
}
