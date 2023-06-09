package ru.asvronsky.scrapper.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ru.asvronsky.scrapper.dto.client.ListStackOverflowResponse;
import ru.asvronsky.scrapper.dto.client.StackOverflowResponse;

@Disabled
public class StackOverflowClientTest {
    @Test
    public void basicTest() {
        StackOverflowClient stackOverflowClient = StackOverflowClient.create();
        ListStackOverflowResponse response = stackOverflowClient.getStackOverflowData("1642028");
        assertNotNull(response);
        assertNotNull(response.items());
        assertEquals(1, response.items().size());
        List<StackOverflowResponse> items = response.items();
        assertEquals(OffsetDateTime.of(
                        LocalDateTime.ofEpochSecond(1256799465, 0, ZoneOffset.UTC), 
                        ZoneOffset.UTC), 
                    items.get(0).creationDate());
    }
}
