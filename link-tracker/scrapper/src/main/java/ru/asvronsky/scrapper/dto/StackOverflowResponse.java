package ru.asvronsky.scrapper.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOverflowResponse(
    @JsonProperty("creation_date") OffsetDateTime creationDate
) {
    
}
