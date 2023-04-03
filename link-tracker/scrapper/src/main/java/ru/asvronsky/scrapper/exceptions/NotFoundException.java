package ru.asvronsky.scrapper.exceptions;

public class NotFoundException extends RestResponseException {

    public NotFoundException(String message) {
        super(message);
    }
    
}
