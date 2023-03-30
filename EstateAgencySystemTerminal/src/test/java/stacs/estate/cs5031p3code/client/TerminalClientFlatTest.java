package stacs.estate.cs5031p3code.client;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class TerminalClientFlatTest {
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

        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.OK.value());
        map.put("message", "");
        map.put("data", null);
        successVoidJson = new JSONObject(map).toString();
    }

    @Test
    void createFlatByBuildingIdTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.createFlatByBuildingId("key", "123", "name", "area");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("POST", request.getMethod());
        assertEquals("/flat/create/123", request.getPath());

        JsonContent<Object> body = jsonTester.from(request.getBody().readUtf8());
        assertThat(body).extractingJsonPathStringValue("$.buildingId").isEqualTo("123");
        assertThat(body).extractingJsonPathStringValue("$.flatName").isEqualTo("name");
        assertThat(body).extractingJsonPathStringValue("$.flatArea").isEqualTo("area");
    }

    @Test
    void deleteFlatByIdTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.deleteFlatById("keyDeleteId", "flatId");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("DELETE", request.getMethod());
        assertEquals("/flat/delete/flatId", request.getPath());
        assertEquals("keyDeleteId", request.getHeader("user_key"));
    }

    @Test
    void updateFlatByIdTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.updateFlatById("key", "flatId", "name", "", "", "");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("PUT", request.getMethod());
        assertEquals("/flat/update/flatId", request.getPath());
        assertEquals("key", request.getHeader("user_key"));
    }

    @Test
    void updateFlatByIdEmptyTest() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        String response = client.updateFlatById("key", "flatId", "", "", "", "");

        assertEquals("Nothing to update!", response);
    }


    @Test
    void listAllFlatsTest() throws InterruptedException {
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

        client.listAllFlats("keyListFlats");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/flat/list", request.getPath());
        assertEquals("keyListFlats", request.getHeader("user_key"));
    }

    @Test
    void listAllFlatsFailureTest() throws InterruptedException {
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        map.put("message", "");
        map.put("data", new ArrayList<>());
        String jsonString = new JSONObject(map).toString();

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(jsonString)
        );
        assertThrows(WebClientException.class,
                () -> client.listAllFlats("keyListFlats"));

        RecordedRequest request = mockWebServer.takeRequest();
    }

    @Test
    void listAllFlatsByBuildingIdTest() throws InterruptedException {
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

        client.listAllFlatsByBuildingId("keyListFlats", "buildingId");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/flat/list/buildingId", request.getPath());
        assertEquals("keyListFlats", request.getHeader("user_key"));
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
}
