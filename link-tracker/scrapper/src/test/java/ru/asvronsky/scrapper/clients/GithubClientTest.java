package ru.asvronsky.scrapper.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ru.asvronsky.scrapper.dto.client.GithubResponse;

@Disabled
public class GithubClientTest {
    
    @Test
    public void basicTest() {
        GithubClient githubClient = GithubClient.create("");
        GithubResponse response = githubClient.getGihubData("mojombo", "grit");
        assertNotNull(response);
        assertEquals(OffsetDateTime.parse("2007-10-29T14:37:16Z"), response.createdAt());
    }
}
