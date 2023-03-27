package ru.asvronsky.scrapper.exceptions;

import lombok.Getter;

@Getter
public abstract class RestResponseException extends RuntimeException {
    private final String description;
    private final String code;
    
    public RestResponseException(String message, String description, String code, Exception cause) {
        super(message, cause);
        this.description = description;
        this.code = code;
    }

    public RestResponseException(String message, String description, String code) {
        this(message, description, code, null);
    }
}
