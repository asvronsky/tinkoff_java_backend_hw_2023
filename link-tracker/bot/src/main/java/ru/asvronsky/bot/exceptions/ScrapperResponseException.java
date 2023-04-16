package ru.asvronsky.bot.exceptions;

import lombok.Getter;
import ru.asvronsky.scrapper.dto.controller.ApiErrorResponse;

@Getter
public class ScrapperResponseException extends RuntimeException {
    private final ApiErrorResponse response;

    public ScrapperResponseException(ApiErrorResponse response) {
        this.response = response;
    }
    
}
