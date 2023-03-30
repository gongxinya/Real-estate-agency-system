package stacs.estate.cs5031p3code.client;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TerminalClientRoleTest {
    private static MockWebServer mockWebServer;
    private TerminalClient client;
    private String successVoidJson;
    private String failureVoidJson;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void initialise() {
        String root = String.format("http://localhost:%s", mockWebServer.getPort());
        client = new TerminalClient(root);

        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.OK.value());
        map.put("message", "");
        map.put("data", null);
        successVoidJson = new JSONObject(map).toString();

        Map<String, Object> map2 = new HashMap<>();
        map2.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        map2.put("message", "");
        map2.put("data", null);
        failureVoidJson = new JSONObject(map2).toString();
    }

    @Test
    void assignRoleToUserSuccessTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.assignRoleToUser("key", "user2", "role1");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("POST", request.getMethod());
        assertEquals("/user/role/user2/role1", request.getPath());
        assertEquals("key", request.getHeader("user_key"));
    }

    @Test
    void assignRoleToUserFailureTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(failureVoidJson)
        );

        assertThrows(WebClientException.class,
                () -> client.assignRoleToUser("key", "user2", "role1"));

        RecordedRequest request = mockWebServer.takeRequest();
    }

    @Test
    void deleteRoleForUserTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.deleteRoleForUser("key", "04", "32");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("DELETE", request.getMethod());
        assertEquals("/user/role/04/32", request.getPath());
        assertEquals("key", request.getHeader("user_key"));
    }

    @Test
    void listRoleForUserTest() throws InterruptedException {
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.OK.value());
        map.put("message", "");
        map.put("data", new ArrayList<>());
        String jsonString = new JSONObject(map).toString();

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(jsonString)
        );

        client.listRoleForUser("key", "user07");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/user/role/list/user07", request.getPath());
        assertEquals("key", request.getHeader("user_key"));
    }

    @Test
    void listAllRolesTest() throws InterruptedException {
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.OK.value());
        map.put("message", "");
        map.put("data", new ArrayList<>());
        String jsonString = new JSONObject(map).toString();

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(jsonString)
        );

        client.listAllRoles("key");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/role/list", request.getPath());
        assertEquals("key", request.getHeader("user_key"));
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
}
