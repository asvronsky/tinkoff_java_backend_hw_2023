package ru.asvronsky.scrapper.exceptions;

public abstract class RestResponseException extends RuntimeException {
    
    public RestResponseException(String message) {
        super(message);
    }
}
