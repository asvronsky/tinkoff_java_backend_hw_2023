package ru.asvronsky.bot.exceptions;

public class InvalidRequestFormatException extends RestResponseException{

    public InvalidRequestFormatException(String message) {
        super(message);
    }

}
