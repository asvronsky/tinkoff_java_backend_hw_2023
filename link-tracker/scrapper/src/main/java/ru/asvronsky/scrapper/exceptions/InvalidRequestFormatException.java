package ru.asvronsky.scrapper.exceptions;

public class InvalidRequestFormatException extends RestResponseException{

    public InvalidRequestFormatException(String message) {
        super(message);
    }

}
