package stacs.estate.cs5031p3code.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import stacs.estate.cs5031p3code.handler.ResponseHandler;

import java.util.HashMap;
import java.util.Map;


public class TerminalClient {
    private final WebClient client;

    public TerminalClient(String root) {
        this.client = WebClient.create(root);
    }

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

    public String logout(String userKey) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/user/logout")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    public String viewUser(String userKey) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/user/view")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseUser(response);
    }

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

    public String deleteUserById(String userKey, String userId) throws WebClientException {
        Map<?, ?> response = client.delete()
                .uri("/user/delete/" + userId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    public String listAllUsers(String userKey) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/user/list")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseListUser(response);
    }

    public String listAllBuildings(String userKey) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/building/list")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseListBuilding(response);
    }

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

    public String deleteBuildingById(String userKey, String buildingId) throws WebClientException {
        Map<?, ?> response = client.delete()
                .uri("/building/delete/" + buildingId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

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

    public String deleteFlatById(String userKey, String flatId) throws WebClientException {
        Map<?, ?> response = client.delete()
                .uri("/flat/delete/" + flatId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

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

    public String listAllFlats(String userKey) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/flat/list")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseListFlat(response);
    }

    public String listAllFlatsByBuildingId(String userKey, String buildingId) throws WebClientException {
        Map<?, ?> response = client.get()
                .uri("/flat/list/" + buildingId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseListFlat(response);
    }

    public String assignRoleToUser(String userKey, String userId, String roleId) throws WebClientException {
        Map<?, ?> response = client.post()
                .uri("/user/role/" + userId + "/" + roleId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    public String deleteRoleForUser(String userKey, String userId, String roleId) throws WebClientException {
        Map<?, ?> response = client.delete()
                .uri("/user/role/" + userId + "/" + roleId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseVoid(response);
    }

    public String listRoleForUser(String userKey, String userId) {
        Map<?, ?> response = client.get()
                .uri("/user/role/list/" + userId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseListRole(response);
    }

    public String listAllRoles(String userKey) {
        Map<?, ?> response = client.get()
                .uri("/role/list")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return ResponseHandler.parseListRole(response);
    }
}
