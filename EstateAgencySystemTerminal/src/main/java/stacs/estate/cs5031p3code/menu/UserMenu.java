package stacs.estate.cs5031p3code.menu;

import org.springframework.web.reactive.function.client.WebClientException;
import stacs.estate.cs5031p3code.client.TerminalClient;

import java.util.Scanner;

public class UserMenu implements Menu {
    private final Scanner scanner;
    private final TerminalClient client;
    private final String userKey;
    private static final String DISPLAY = """
            \nUSER MENU
            [0] View my profile
            [1] Update my profile
            [2] [Admin] Update user by ID
            [3] [Admin] Delete user by ID
            [4] [Admin] List all users
            [b] Back to MAIN MENU
            """;

    public UserMenu(Scanner scanner, TerminalClient client, String userKey) {
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
                    case "b" -> {
                        return;
                    }
                    case "0" -> {
                        response = client.viewUser(userKey);
                    }
                    case "1" -> {
                        System.out.println("Please enter new details below (empty string will be ignored)");
                        System.out.println("Email: ");
                        String email = scanner.nextLine();
                        System.out.println("Password: ");
                        String password = scanner.nextLine();
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Phone: ");
                        String phone = scanner.nextLine();
                        System.out.println("Address: ");
                        String address = scanner.nextLine();
                        response = client.updateUser(userKey, name, phone, email, address, password);
                    }
                    case "2" -> {
                        System.out.println("Please enter the id of the user to be updated: ");
                        String userId = scanner.nextLine();
                        System.out.println("Please enter new details below (empty string will be ignored)");
                        System.out.println("Email: ");
                        String email = scanner.nextLine();
                        System.out.println("Password: ");
                        String password = scanner.nextLine();
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Phone: ");
                        String phone = scanner.nextLine();
                        System.out.println("Address: ");
                        String address = scanner.nextLine();
                        response = client.updateUserById(userKey, userId, name, phone, email, address, password);
                    }
                    case "3" -> {
                        System.out.println("Please enter the id of the user to be updated: ");
                        String userId = scanner.nextLine();
                        response = client.deleteUserById(userKey, userId);
                    }
                    case "4" -> {
                        response = client.listAllUsers(userKey);
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
