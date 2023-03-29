package stacs.estate.cs5031p3code;

import stacs.estate.cs5031p3code.client.TerminalClient;
import stacs.estate.cs5031p3code.menu.Menu;
import stacs.estate.cs5031p3code.menu.WelcomeMenu;

import java.util.Scanner;

/**
 * The entry point of the terminal client.
 *
 * @author 190005675
 */
public class TerminalClientMain {
    private static final String root = "http://localhost:8080";

    public static void main(String[] args) {
        TerminalClient client = new TerminalClient(root);

        Scanner scanner = new Scanner(System.in);

        Menu welcomeMenu = new WelcomeMenu(scanner, client);
        welcomeMenu.run();
        scanner.close();
        System.exit(0);
    }
}
