package stacs.estate.cs5031p3code.client;

import com.alibaba.fastjson.JSONObject;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import stacs.estate.cs5031p3code.model.po.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TerminalClientUserTest {
    private static MockWebServer mockWebServer;
    private TerminalClient client;
    private String successVoidJson;
    private final BasicJsonTester jsonTester = new BasicJsonTester(this.getClass());

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void initialise() {
        String root = String.format("http://localhost:%s", mockWebServer.getPort());
        client = new TerminalClient(root);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.OK.value());
        jsonObject.put("message", "");
        jsonObject.put("data", null);
        successVoidJson = jsonObject.toJSONString();
    }

    @Test
    void loginTest() throws InterruptedException {
        // prepare json string
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.OK.value());
        jsonObject.put("message", "");
        Map<String, String> map = new HashMap<>();
        map.put("user_key", "key");
        jsonObject.put("data", map);
        String jsonString = jsonObject.toJSONString();

        // mock server
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(jsonString)
        );

        client.login("email", "password");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("POST", request.getMethod());
        assertEquals("/user/login", request.getPath());

        JsonContent<Object> body = jsonTester.from(request.getBody().readUtf8());
        assertThat(body).extractingJsonPathStringValue("$.userEmail").isEqualTo("email");
        assertThat(body).extractingJsonPathStringValue("$.userPassword").isEqualTo("password");
    }

    @Test
    void registerTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.register("123", "456", "name");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("POST", request.getMethod());
        assertEquals("/user/create", request.getPath());

        JsonContent<Object> body = jsonTester.from(request.getBody().readUtf8());
        assertThat(body).extractingJsonPathStringValue("$.userEmail").isEqualTo("123");
        assertThat(body).extractingJsonPathStringValue("$.userPassword").isEqualTo("456");
        assertThat(body).extractingJsonPathStringValue("$.userName").isEqualTo("name");
    }

    @Test
    void logoutTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.logout("keyLogout");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/user/logout", request.getPath());
        assertEquals("keyLogout", request.getHeader("user_key"));
    }

    @Test
    void viewUserTest() throws InterruptedException {
        User user = mock(User.class);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.OK.value());
        jsonObject.put("message", "");
        jsonObject.put("data", user);
        String jsonString = jsonObject.toJSONString();

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(jsonString)
        );

        client.viewUser("keyView");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/user/view", request.getPath());
        assertEquals("keyView", request.getHeader("user_key"));
    }

    @Test
    void updateUserTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.updateUser("keyUpdate", "", "", "", "", "");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("PUT", request.getMethod());
        assertEquals("/user/update", request.getPath());
        assertEquals("keyUpdate", request.getHeader("user_key"));
    }

    @Test
    void updateUserByIdTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.updateUserById("keyUpdateId", "byId", "", "", "", "", "");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("PUT", request.getMethod());
        assertEquals("/user/update/byId", request.getPath());
        assertEquals("keyUpdateId", request.getHeader("user_key"));
    }

    @Test
    void deleteUserByIdTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.deleteUserById("keyDeleteId", "byId");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("DELETE", request.getMethod());
        assertEquals("/user/delete/byId", request.getPath());
        assertEquals("keyDeleteId", request.getHeader("user_key"));
    }

    @Test
    void listAllUsersTest() throws InterruptedException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.OK.value());
        jsonObject.put("message", "");
        jsonObject.put("data", new ArrayList<>());
        String jsonString = jsonObject.toJSONString();

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(jsonString)
        );

        client.listAllUsers("keyList");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/user/list", request.getPath());
        assertEquals("keyList", request.getHeader("user_key"));
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
}