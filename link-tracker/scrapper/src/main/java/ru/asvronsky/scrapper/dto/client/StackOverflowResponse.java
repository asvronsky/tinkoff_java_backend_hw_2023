package ru.asvronsky.scrapper.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOverflowResponse(
    @JsonProperty("answer_count") Integer answerCount,
    @JsonProperty("score") Integer score
) {
    
}
