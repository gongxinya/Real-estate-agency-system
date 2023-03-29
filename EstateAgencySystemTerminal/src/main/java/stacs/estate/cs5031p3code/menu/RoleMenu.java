package stacs.estate.cs5031p3code.menu;

import org.springframework.web.reactive.function.client.WebClientException;
import stacs.estate.cs5031p3code.client.TerminalClient;

import java.util.Scanner;

/**
 * An implementation of the {@link Menu}.
 * This is the role menu which allows admins to control roles of users.
 *
 * @author 190005675
 */
public class RoleMenu implements Menu {
    private final Scanner scanner;
    private final TerminalClient client;
    private final String userKey;
    private static final String DISPLAY = """
            \nROLE MENU
            [0] [Admin] Assign role to user by ID
            [1] [Admin] Remove role from user by ID
            [2] [Admin] List role for user by ID
            [3] [Admin] List all roles
            [b] Back to MAIN MENU
            """;

    public RoleMenu(Scanner scanner, TerminalClient client, String userKey) {
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
                        // assign role
                        System.out.println("Please enter the user ID: ");
                        String userId = scanner.nextLine();
                        if (userId.equals("")) {
                            response = "User ID cannot be empty!";
                            break;
                        }
                        System.out.println("Please enter the role ID: ");
                        String roleId = scanner.nextLine();
                        if (roleId.equals("")) {
                            response = "Role ID cannot be empty!";
                            break;
                        }
                        response = client.assignRoleToUser(userKey, userId, roleId);
                    }
                    case "1" -> {
                        // remove role
                        System.out.println("Please enter the user ID: ");
                        String userId = scanner.nextLine();
                        if (userId.equals("")) {
                            response = "User ID cannot be empty!";
                            break;
                        }
                        System.out.println("Please enter the role ID: ");
                        String roleId = scanner.nextLine();
                        if (roleId.equals("")) {
                            response = "Role ID cannot be empty!";
                            break;
                        }
                        response = client.deleteRoleForUser(userKey, userId, roleId);
                    }
                    case "2" -> {
                        // list role for user
                        System.out.println("Please enter the user ID to view their role: ");
                        String userId = scanner.nextLine();
                        if (userId.equals("")) {
                            response = "ID cannot be empty!";
                            break;
                        }
                        response = client.listRoleForUser(userKey, userId);
                    }
                    case "3" -> {
                        // list all roles
                        response = client.listAllRoles(userKey);
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
