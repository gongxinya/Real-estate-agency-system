package stacs.estate.cs5031p3code.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import stacs.estate.cs5031p3code.model.po.Building;
import stacs.estate.cs5031p3code.model.po.User;
import stacs.estate.cs5031p3code.utils.ResponseResult;

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

        String response = client.post()
                .uri("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ResponseResult<Map<String, String>> responseResult = JSON.parseObject(response, ResponseResult.class);

        if (responseResult.getCode().equals(HttpStatus.OK.value())) {
            // login successful
            return responseResult.getData().get("user_key");
        }
        return null;
    }

    public String register(String email, String password, String name) throws WebClientException {
        Map<String, String> bodyValues = new HashMap<>();
        bodyValues.put("userEmail", email);
        bodyValues.put("userPassword", password);
        bodyValues.put("userName", name);

        String response = client.post()
                .uri("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ResponseResult<?> responseResult = JSON.parseObject(response, ResponseResult.class);

        return responseResult.getMessage();
    }

    public String logout(String userKey) throws WebClientException {
        String response = client.get()
                .uri("/user/logout")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ResponseResult<?> responseResult = JSON.parseObject(response, ResponseResult.class);
        return responseResult.getMessage();
    }

    public String viewUser(String userKey) throws WebClientException {
        String response = client.get()
                .uri("/user/view")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ResponseResult<?> responseResult = JSON.parseObject(response, ResponseResult.class);
        if (responseResult.getCode().equals(HttpStatus.OK.value())) {
            String data = responseResult.getData().toString();
            User user = JSON.parseObject(data, User.class);
            return user.toString();
        }
        return responseResult.getMessage();
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

        String response = client.put()
                .uri("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("user_key", userKey)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ResponseResult<?> responseResult = JSON.parseObject(response, ResponseResult.class);
        return responseResult.getMessage();
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

        String response = client.put()
                .uri("/user/update/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("user_key", userKey)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ResponseResult<?> responseResult = JSON.parseObject(response, ResponseResult.class);
        return responseResult.getMessage();
    }

    public String deleteUserById(String userKey, String userId) throws WebClientException {
        String response = client.delete()
                .uri("/user/delete/" + userId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ResponseResult<?> responseResult = JSON.parseObject(response, ResponseResult.class);
        return responseResult.getMessage();
    }

    public String listAllUsers(String userKey) throws WebClientException {
        String response = client.get()
                .uri("/user/list")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ResponseResult<?> responseResult = JSON.parseObject(response, ResponseResult.class);
        if (responseResult.getCode().equals(HttpStatus.OK.value())) {
            JSONArray jsonArray = (JSONArray) responseResult.getData();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                User user = JSON.parseObject(obj.toJSONString(), User.class);
                sb.append(user.toString());
                sb.append("\n");
            }
            return sb.toString();
        }
        return responseResult.getMessage();
    }

    public String listAllBuildings(String userKey) throws WebClientException {
        String response = client.get()
                .uri("/building/list")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ResponseResult<?> responseResult = JSON.parseObject(response, ResponseResult.class);
        if (responseResult.getCode().equals(HttpStatus.OK.value())) {
            JSONArray jsonArray = (JSONArray) responseResult.getData();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Building building = JSON.parseObject(obj.toJSONString(), Building.class);
                sb.append(building.toString());
                sb.append("\n");
            }
            return sb.toString();
        }
        return responseResult.getMessage();
    }

    public String createBuilding(String userKey, String name, String address) throws WebClientException {
        Map<String, String> bodyValues = new HashMap<>();
        bodyValues.put("buildingName", name);
        bodyValues.put("buildingAddress", address);

        String response = client.post()
                .uri("/building/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("user_key", userKey)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ResponseResult<?> responseResult = JSON.parseObject(response, ResponseResult.class);
        return responseResult.getMessage();
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

        String response = client.put()
                .uri("/building/update/" + buildingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("user_key", userKey)
                .body(BodyInserters.fromValue(bodyValues))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ResponseResult<?> responseResult = JSON.parseObject(response, ResponseResult.class);
        return responseResult.getMessage();
    }

    public String deleteBuildingById(String userKey, String buildingId) throws WebClientException {
        String response = client.delete()
                .uri("/building/delete/" + buildingId)
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ResponseResult<?> responseResult = JSON.parseObject(response, ResponseResult.class);
        return responseResult.getMessage();
    }

}
