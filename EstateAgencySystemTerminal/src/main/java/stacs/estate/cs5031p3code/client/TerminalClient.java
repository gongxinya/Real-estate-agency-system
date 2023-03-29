package stacs.estate.cs5031p3code.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import stacs.estate.cs5031p3code.handler.ResponseHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * A client to make HTTP requests to the server.
 *
 * @author 190005675
 */
public class TerminalClient {

    /**
     * Non-blocking, reactive client to perform HTTP requests.
     */
    private final WebClient client;


    /**
     * Constructor of a terminal client.
     *
     * @param root the root url
     */
    public TerminalClient(String root) {
        this.client = WebClient.create(root);
    }

    /**
     * Log a user into the system.
     *
     * @param email    user email
     * @param password user password
     * @return user key if login successful, null otherwise
     * @throws WebClientException in case of errors
     */
    public String login(String email, String password) throws WebClientException {
        Map<String, String> bodyValues = new HashMap<>();
        bodyValues.put("userEmail", email);
        bodyValues.put("userPassword", password);

        Map<?, ?> response = client.post()
                .uri("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseUserKey(response);
    }

    /**
     * Register a new user.
     *
     * @param email    user email
     * @param password user password
     * @param name     name of the user
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String register(String email, String password, String name) throws WebClientException {
        Map<String, String> bodyValues = new HashMap<>();
        bodyValues.put("userEmail", email);
        bodyValues.put("userPassword", password);
        bodyValues.put("userName", name);

        Map<?, ?> response = client.post()
                .uri("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * Log out a user in this terminal.
     *
     * @param userKey JSON web token to identify the user
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String logout(String userKey) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/user/logout")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * View user profile.
     *
     * @param userKey JSON web token to identify the user
     * @return a string containing user details
     * @throws WebClientException in case of errors
     */
    public String viewUser(String userKey) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/user/view")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseUser(response);
    }

    /**
     * Update user profile.
     *
     * @param userKey JSON web token to identify the user
     * @param name    new name
     * @param phone   new phone
     * @param email   new email
     * @param address new address
     * @param pwd     new password
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String updateUser(String userKey, String name, String phone,
                             String email, String address, String pwd) throws WebClientException {
        Map<String, String> bodyValues = new HashMap<>();
        if (!name.equals("")) {
            bodyValues.put("userName", name);
        }
        if (!phone.equals("")) {
            bodyValues.put("userPhone", phone);
        }
        if (!email.equals("")) {
            bodyValues.put("userEmail", email);
        }
        if (!address.equals("")) {
            bodyValues.put("userAddress", address);
        }
        if (!pwd.equals("")) {
            bodyValues.put("userPassword", pwd);
        }

        Map<?, ?> response = client.put()
                .uri("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("user_key", userKey)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * Update user profile by user id.
     *
     * @param userKey JSON web token to identify the user
     * @param userId  id of the user to be updated
     * @param name    new name
     * @param phone   new phone
     * @param email   new email
     * @param address new address
     * @param pwd     new password
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String updateUserById(String userKey, String userId, String name, String phone,
                                 String email, String address, String pwd) throws WebClientException {
        Map<String, String> bodyValues = new HashMap<>();
        if (!name.equals("")) {
            bodyValues.put("userName", name);
        }
        if (!phone.equals("")) {
            bodyValues.put("userPhone", phone);
        }
        if (!email.equals("")) {
            bodyValues.put("userEmail", email);
        }
        if (!address.equals("")) {
            bodyValues.put("userAddress", address);
        }
        if (!pwd.equals("")) {
            bodyValues.put("userPassword", pwd);
        }

        Map<?, ?> response = client.put()
                .uri("/user/update/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("user_key", userKey)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * Delete the user with the given user id.
     *
     * @param userKey JSON web token to identify the user
     * @param userId  user id
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String deleteUserById(String userKey, String userId) throws WebClientException {
        Map<?, ?> response = client.delete()
                .uri("/user/delete/" + userId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * List all users in the system.
     *
     * @param userKey JSON web token to identify the user
     * @return a list of users parsed as a string
     * @throws WebClientException in case of errors
     */
    public String listAllUsers(String userKey) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/user/list")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseListUser(response);
    }

    /**
     * List all buildings in the system.
     *
     * @param userKey JSON web token to identify the user
     * @return a list of buildings parsed as a string
     * @throws WebClientException in case of errors
     */
    public String listAllBuildings(String userKey) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/building/list")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseListBuilding(response);
    }

    /**
     * Add a building to the system.
     *
     * @param userKey JSON web token to identify the user
     * @param name    building name
     * @param address building address
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String createBuilding(String userKey, String name, String address) throws WebClientException {
        Map<String, String> bodyValues = new HashMap<>();
        bodyValues.put("buildingName", name);
        bodyValues.put("buildingAddress", address);

        Map<?, ?> response = client.post()
                .uri("/building/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("user_key", userKey)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * Update a building in the system.
     *
     * @param userKey    JSON web token to identify the user
     * @param buildingId id of the building to be updated
     * @param name       new building name
     * @param address    new building address
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String updateBuildingById(String userKey, String buildingId, String name, String address)
            throws WebClientException {
        Map<String, String> bodyValues = new HashMap<>();
        if (!name.equals("")) {
            bodyValues.put("buildingName", name);
        }
        if (!address.equals("")) {
            bodyValues.put("buildingAddress", address);
        }

        Map<?, ?> response = client.put()
                .uri("/building/update/" + buildingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("user_key", userKey)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * Delete a building by building id.
     *
     * @param userKey    JSON web token to identify the user
     * @param buildingId the id of the building to be deleted
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String deleteBuildingById(String userKey, String buildingId) throws WebClientException {
        Map<?, ?> response = client.delete()
                .uri("/building/delete/" + buildingId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * Add a flat to a building.
     *
     * @param userKey    JSON web token to identify the user
     * @param buildingId building id
     * @param flatName   flat name
     * @param flatArea   flat area
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String createFlatByBuildingId(String userKey, String buildingId, String flatName, String flatArea) throws WebClientException {
        Map<String, String> bodyValues = new HashMap<>();
        bodyValues.put("buildingId", buildingId);
        bodyValues.put("flatName", flatName);
        bodyValues.put("flatArea", flatArea);

        Map<?, ?> response = client.post()
                .uri("/flat/create/" + buildingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("user_key", userKey)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * Delete flat with flat id.
     *
     * @param userKey JSON web token to identify the user
     * @param flatId  flat id
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String deleteFlatById(String userKey, String flatId) throws WebClientException {
        Map<?, ?> response = client.delete()
                .uri("/flat/delete/" + flatId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * Update a flat with flat id.
     *
     * @param userKey JSON web token to identify the user
     * @param flatId  flat id
     * @param name    new flat name
     * @param area    new flat area
     * @param date    new flat sold date
     * @param price   flat price
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String updateFlatById(String userKey, String flatId,
                                 String name, String area, String date, String price) throws WebClientException {
        Map<String, String> bodyValues = new HashMap<>();
        if (!name.equals("")) {
            bodyValues.put("flatName", name);
        }
        if (!area.equals("")) {
            bodyValues.put("flatArea", area);
        }
        if (!date.equals("")) {
            bodyValues.put("flatSoldOutDate", date);
        }
        if (!price.equals("")) {
            bodyValues.put("flatPrice", price);
        }

        Map<?, ?> response = client.put()
                .uri("/flat/update/" + flatId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("user_key", userKey)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * List all flats in the system.
     *
     * @param userKey JSON web token to identify the user
     * @return a list of flats parsed as a string
     * @throws WebClientException in case of errors
     */
    public String listAllFlats(String userKey) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/flat/list")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseListFlat(response);
    }

    /**
     * List all flats inside a building.
     *
     * @param userKey    JSON web token to identify the user
     * @param buildingId building id
     * @return a list of flats parsed as a string
     * @throws WebClientException in case of errors
     */
    public String listAllFlatsByBuildingId(String userKey, String buildingId) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/flat/list/" + buildingId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseListFlat(response);
    }

    /**
     * Assign a role to a user.
     *
     * @param userKey JSON web token to identify the user
     * @param userId  user id
     * @param roleId  role id
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String assignRoleToUser(String userKey, String userId, String roleId) throws WebClientException {
        Map<?, ?> response = client.post()
                .uri("/user/role/" + userId + "/" + roleId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * Delete a role for a user.
     *
     * @param userKey JSON web token to identify the user
     * @param userId  user id
     * @param roleId  role id
     * @return the message from the server
     * @throws WebClientException in case of errors
     */
    public String deleteRoleForUser(String userKey, String userId, String roleId) throws WebClientException {
        Map<?, ?> response = client.delete()
                .uri("/user/role/" + userId + "/" + roleId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    /**
     * Show the role of the user.
     *
     * @param userKey JSON web token to identify the user
     * @param userId  user id
     * @return the role of the user
     * @throws WebClientException in case of errors
     */
    public String listRoleForUser(String userKey, String userId) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/user/role/list/" + userId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseListRole(response);
    }

    /**
     * List all roles in the system.
     *
     * @param userKey JSON web token to identify the user
     * @return a list of roles parsed as a string
     * @throws WebClientException in case of errors
     */
    public String listAllRoles(String userKey) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/role/list")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseListRole(response);
    }
}
