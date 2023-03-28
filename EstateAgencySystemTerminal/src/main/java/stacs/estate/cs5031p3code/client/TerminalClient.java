package stacs.estate.cs5031p3code.client;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

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
        ResponseResult<Map<String, String>> responseResult = JSON.parseObject(response, ResponseResult.class);

        return responseResult.getMessage();
    }

    public String logout(String userKey) throws WebClientException {
        String response = client.get()
                .uri("/user/logout")
                .header("user_key", userKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ResponseResult<Map<String, String>> responseResult = JSON.parseObject(response, ResponseResult.class);
        return responseResult.getMessage();
    }
}
