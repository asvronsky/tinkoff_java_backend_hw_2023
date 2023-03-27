package ru.asvronsky.scrapper.exceptions;

public class ChatIdNotFoundException extends RestResponseException {

    public ChatIdNotFoundException(String message, String code) {
        super(message, "No registered chat with this id exists", code);
    }
    
}
