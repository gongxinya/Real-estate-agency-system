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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TerminalClientBuildingTest {
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
    void listAllBuildingsTest() throws InterruptedException {
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

        client.listAllBuildings("keyListBuilding");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/building/list", request.getPath());
        assertEquals("keyListBuilding", request.getHeader("user_key"));
    }

    @Test
    void createBuildingTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.createBuilding("keyCreateBuilding", "name", "addr");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("POST", request.getMethod());
        assertEquals("/building/create", request.getPath());

        JsonContent<Object> body = jsonTester.from(request.getBody().readUtf8());
        assertThat(body).extractingJsonPathStringValue("$.buildingName").isEqualTo("name");
        assertThat(body).extractingJsonPathStringValue("$.buildingAddress").isEqualTo("addr");
    }

    @Test
    void updateBuildingByIdTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.updateBuildingById("keyUpdateBuildingId", "byId", "name", "");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("PUT", request.getMethod());
        assertEquals("/building/update/byId", request.getPath());
        assertEquals("keyUpdateBuildingId", request.getHeader("user_key"));
    }

    @Test
    void updateBuildingByIdEmptyTest() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        String response = client.updateBuildingById("key", "id2", "", "");
        assertEquals("Nothing to update!", response);
    }

    @Test
    void deleteBuildingByIdTest() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(successVoidJson)
        );

        client.deleteBuildingById("keyDeleteId", "byId");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("DELETE", request.getMethod());
        assertEquals("/building/delete/byId", request.getPath());
        assertEquals("keyDeleteId", request.getHeader("user_key"));
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
}
