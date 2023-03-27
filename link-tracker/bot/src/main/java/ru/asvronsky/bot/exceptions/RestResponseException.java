package ru.asvronsky.bot.exceptions;

public abstract class RestResponseException extends RuntimeException {
    
    public RestResponseException(String message) {
        super(message);
    }
}
