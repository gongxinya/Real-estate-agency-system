package stacs.estate.cs5031p3code.menu;

import org.springframework.web.reactive.function.client.WebClientException;
import stacs.estate.cs5031p3code.client.TerminalClient;

import java.util.Scanner;

/**
 * An implementation of the {@link Menu}.
 * This is the flat menu which allows user to access flat data.
 *
 * @author 190005675
 */
public class FlatMenu implements Menu {
    private final Scanner scanner;
    private final TerminalClient client;
    private final String userKey;
    private static final String DISPLAY = """
            \nFLAT MENU
            [0] List all flats
            [1] List all flats by building ID
            [2] [Admin/Manager] Create a flat
            [3] [Admin/Manager] Update a flat by ID
            [4] [Admin/Manager] Delete a flat by ID
            [b] Back to MAIN MENU
            """;

    public FlatMenu(Scanner scanner, TerminalClient client, String userKey) {
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
                        // list all flats
                        response = client.listAllFlats(userKey);
                    }
                    case "1" -> {
                        // list all flats by building id
                        System.out.println("Please enter the building id: ");
                        String buildingId = scanner.nextLine();
                        if (buildingId.equals("")) {
                            response = "ID cannot be empty!";
                            break;
                        }
                        response = client.listAllFlatsByBuildingId(userKey, buildingId);
                    }
                    case "2" -> {
                        // create a flat
                        System.out.println("Please enter the building id to add a flat to: ");
                        String buildingId = scanner.nextLine();
                        if (buildingId.equals("")) {
                            response = "ID cannot be empty!";
                            break;
                        }
                        System.out.println("Please enter flat details below (empty string will be ignored)");
                        System.out.println("Flat Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Flat Area: ");
                        String area = scanner.nextLine();
                        response = client.createFlatByBuildingId(userKey, buildingId, name, area);
                    }
                    case "3" -> {
                        // update a flat
                        System.out.println("Please enter the ID of the flat to be updated: ");
                        String flatId = scanner.nextLine();
                        if (flatId.equals("")) {
                            response = "ID cannot be empty!";
                            break;
                        }
                        System.out.println("Please enter new details below (empty string will be ignored)");
                        System.out.println("Flat Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Flat Area: ");
                        String area = scanner.nextLine();
                        System.out.println("Flat Sold Date(yyyy-MM-dd HH:mm:ss): ");
                        String date = scanner.nextLine();
                        System.out.println("Flat Price: ");
                        String price = scanner.nextLine();
                        response = client.updateFlatById(userKey, flatId, name, area, date, price);
                    }
                    case "4" -> {
                        // delete a flat
                        System.out.println("Please enter the ID of the flat to be deleted: ");
                        String flatId = scanner.nextLine();
                        if (flatId.equals("")) {
                            response = "ID cannot be empty!";
                            break;
                        }
                        response = client.deleteFlatById(userKey, flatId);
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

