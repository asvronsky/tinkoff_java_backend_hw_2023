package ru.asvronsky.scrapper.exceptions;

import lombok.Getter;
import ru.asvronsky.shared.shareddto.ApiErrorResponse;

@Getter
public class BotResponseException extends RuntimeException {
    private final ApiErrorResponse apiResponse;
    
    public BotResponseException(ApiErrorResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

}
