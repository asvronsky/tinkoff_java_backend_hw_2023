package ru.asvronsky.bot.exceptions;

import lombok.Getter;
import ru.asvronsky.scrapper.dto.controller.ApiErrorResponse;

@Getter
public class ScrapperResponseException extends RuntimeException {
    private final ApiErrorResponse apiResponse;
    
    public ScrapperResponseException(ApiErrorResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

}
