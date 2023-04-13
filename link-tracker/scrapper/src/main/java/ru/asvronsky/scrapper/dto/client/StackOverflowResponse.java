package ru.asvronsky.scrapper.dto.client;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOverflowResponse(
    @JsonProperty("creation_date") OffsetDateTime creationDate
) {
    
}
