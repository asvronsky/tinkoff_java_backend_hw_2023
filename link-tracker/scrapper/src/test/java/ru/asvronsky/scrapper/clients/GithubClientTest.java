package ru.asvronsky.scrapper.clients;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.OffsetDateTime;

import org.junit.Test;

import ru.asvronsky.scrapper.dto.GithubResponse;

public class GithubClientTest {
    
    @Test
    public void basicTest() {
        GithubClient githubClient = GithubClient.create();
        GithubResponse response = githubClient.getGihubData("mojombo/grit");
        assertNotNull(response);
        assertEquals(OffsetDateTime.parse("2007-10-29T14:37:16Z"), response.createdAt());
    }
}
