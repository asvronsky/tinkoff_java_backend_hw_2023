package ru.asvronsky.scrapper.exceptions;

public abstract class RestResponseException extends RuntimeException {
    
    public RestResponseException(String message) {
        super(message);
    }

    public RestResponseException(String message, Exception cause) {
        super(message, cause);
    }

}
