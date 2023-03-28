package stacs.estate.cs5031p3code.menu;

import org.springframework.web.reactive.function.client.WebClientException;

import stacs.estate.cs5031p3code.client.TerminalClient;

import java.util.Scanner;

public class WelcomeMenu implements Menu {
    private final Scanner scanner;
    private final TerminalClient client;
    private static final String DISPLAY = """
            \nWELCOME
            [0] login
            [1] register
            [x] exit""";

    public WelcomeMenu(Scanner scanner, TerminalClient client) {
        this.scanner = scanner;
        this.client = client;
    }

    @Override
    public void run() {
        String response;
        System.out.println(DISPLAY);
        while (scanner.hasNextLine()) {
            try {
                String input = scanner.nextLine();
                switch (input) {
                    case "x" -> {
                        response = "[Exit] Command line interface closed.";
                        System.out.println(response);
                        scanner.close();
                        return;
                    }
                    case "0" -> {
                        System.out.println("Email: ");
                        String email = scanner.nextLine();
                        System.out.println("Password: ");
                        String password = scanner.nextLine();
                        String userKey = client.login(email, password);
                        if (userKey == null) {
                            response = "Incorrect email or password";
                            break;
                        }
//                        String userKey = "key";
                        Menu userMenu = new MainMenu(scanner, client, userKey);
                        userMenu.run();
                        response = null;
                    }
                    case "1" -> {
                        System.out.println("Email: ");
                        String email = scanner.nextLine();
                        System.out.println("Password: ");
                        String password = scanner.nextLine();
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        response = client.register(email, password, name);
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
        scanner.close();
    }
}
