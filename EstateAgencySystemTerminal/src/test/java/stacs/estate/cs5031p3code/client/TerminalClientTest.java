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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TerminalClientTest {
    private static MockWebServer mockWebServer;
    private TerminalClient client;
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.OK.value());
        jsonObject.put("message", "");
        jsonObject.put("data", null);
        String json = jsonObject.toJSONString();
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(json)
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.OK.value());
        jsonObject.put("message", "");
        jsonObject.put("data", null);
        String json = jsonObject.toJSONString();
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(json)
        );

        client.logout("key");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/user/logout", request.getPath());
        assertEquals("key", request.getHeader("user_key"));
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
}