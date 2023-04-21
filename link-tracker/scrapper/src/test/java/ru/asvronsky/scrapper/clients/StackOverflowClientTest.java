package ru.asvronsky.scrapper.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import ru.asvronsky.scrapper.dto.client.StackOverflowResponse;

public class StackOverflowClientTest {

    public static MockWebServer mockServer;
    public StackOverflowClient stackoverflowClient;

    @BeforeAll
    static void setUp() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockServer.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = mockServer.url("/").toString();
        stackoverflowClient = StackOverflowClient.create(baseUrl, "abstractocelot");
    }
    
    @Test
    public void basicTest() throws IOException, InterruptedException {
        InputStream jsonStream = getClass().getClassLoader()
            .getResourceAsStream("stackoverflow_response.json");
        mockServer.enqueue(
            new MockResponse()
                .setBody(new String(jsonStream.readAllBytes()))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        );

        Optional<StackOverflowResponse> responseOptional = stackoverflowClient.getStackOverflowData(72708612);
        RecordedRequest recordedRequest = mockServer.takeRequest();

        assertTrue(responseOptional.isPresent());
        StackOverflowResponse response = responseOptional.get();
        assertEquals("/questions/72708612?site=stackoverflow&key=abstractocelot", recordedRequest.getPath());
        assertEquals(1, response.answerCount());
        assertEquals(1, response.score());
    }
}
