package ru.asvronsky.scrapper.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import ru.asvronsky.scrapper.dto.client.GithubResponse;


public class GithubClientTest {

    public static MockWebServer mockServer;
    public GithubClient githubClient;

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
        githubClient = GithubClient.create(baseUrl, "");
    }
    
    @Test
    public void basicTest() throws IOException, InterruptedException {
        InputStream jsonStream = getClass().getClassLoader()
            .getResourceAsStream("github_mojombo_grit.json");
        mockServer.enqueue(
            new MockResponse()
                .setBody(new String(jsonStream.readAllBytes()))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        );

        GithubResponse response = githubClient.getGihubData("mojombo", "grit");
        RecordedRequest recordedRequest = mockServer.takeRequest();

        assertEquals("/repos/mojombo/grit", recordedRequest.getPath());
        assertEquals(OffsetDateTime.parse("2020-10-01T03:55:32Z"), response.pushedAt());
    }
}
