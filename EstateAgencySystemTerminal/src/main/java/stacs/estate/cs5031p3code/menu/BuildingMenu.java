package stacs.estate.cs5031p3code.menu;

import org.springframework.web.reactive.function.client.WebClientException;
import stacs.estate.cs5031p3code.client.TerminalClient;

import java.util.Scanner;

public class BuildingMenu implements Menu {
    private final Scanner scanner;
    private final TerminalClient client;
    private final String userKey;
    private static final String DISPLAY = """
            \nBUILDING MENU
            [0] List all buildings
            [1] [Admin] Create a building
            [2] [Admin] Update a building by ID
            [3] [Admin] Delete a building by ID
            [b] Back to MAIN MENU
            """;

    public BuildingMenu(Scanner scanner, TerminalClient client, String userKey) {
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
                        // list all buildings
                        response = client.listAllBuildings(userKey);
                    }
                    case "1" -> {
                        // create a building
                        System.out.println("Building Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Building Address: ");
                        String address = scanner.nextLine();
                        response = client.createBuilding(userKey, name, address);
                    }
                    case "2" -> {
                        // update a building
                        System.out.println("Please enter the id of the building to be updated: ");
                        String buildingId = scanner.nextLine();
                        if (buildingId.equals("")) {
                            response = "ID cannot be empty!";
                            break;
                        }
                        System.out.println("Please enter new details below (empty string will be ignored)");
                        System.out.println("Building Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Building Address: ");
                        String address = scanner.nextLine();
                        response = client.updateBuildingById(userKey, buildingId, name, address);
                    }
                    case "3" -> {
                        // delete a building
                        System.out.println("Please enter the id of the building to be deleted: ");
                        String buildingId = scanner.nextLine();
                        if (buildingId.equals("")) {
                            response = "ID cannot be empty!";
                            break;
                        }
                        response = client.deleteBuildingById(userKey, buildingId);
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
