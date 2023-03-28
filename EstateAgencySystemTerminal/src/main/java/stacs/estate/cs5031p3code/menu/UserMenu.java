package stacs.estate.cs5031p3code.menu;

import stacs.estate.cs5031p3code.client.TerminalClient;

import java.util.Scanner;

public class UserMenu extends MainMenu {
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
        super(scanner, client, userKey);
    }

    @Override
    public void run() {

    }
}
