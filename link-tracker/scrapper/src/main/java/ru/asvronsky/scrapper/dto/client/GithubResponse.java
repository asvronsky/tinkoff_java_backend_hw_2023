package ru.asvronsky.scrapper.dto.client;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubResponse(
    @JsonProperty("pushed_at") OffsetDateTime createdAt
) {
    
}
