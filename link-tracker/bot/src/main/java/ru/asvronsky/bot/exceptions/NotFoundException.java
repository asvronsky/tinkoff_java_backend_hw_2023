package ru.asvronsky.bot.exceptions;

public class NotFoundException extends RestResponseException {

    public NotFoundException(String message) {
        super(message);
    }
    
}
