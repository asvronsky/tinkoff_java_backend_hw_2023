package ru.asvronsky.scrapper.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubResponse(
    @JsonProperty("created_at") OffsetDateTime createdAt
) {
    
}
