package ru.asvronsky.scrapper.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ru.asvronsky.scrapper.dto.client.StackOverflowResponse;

@Disabled
public class StackOverflowClientTest {
    @Test
    public void basicTest() {
        StackOverflowClient stackOverflowClient = StackOverflowClient.create("");
        StackOverflowResponse response = stackOverflowClient.getStackOverflowData(1642028);
        assertNotNull(response);
        assertEquals(OffsetDateTime.of(
                        LocalDateTime.ofEpochSecond(1256799465, 0, ZoneOffset.UTC), 
                        ZoneOffset.UTC), 
                    response.creationDate());
    }
}
