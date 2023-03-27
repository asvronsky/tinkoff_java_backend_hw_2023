package ru.asvronsky.scrapper.exceptions;

public class InvalidRequestFormatException extends RestResponseException{

    public InvalidRequestFormatException(String message, String code, Exception cause) {
        super(message, "Invalid request format", code, cause);
    }

    public InvalidRequestFormatException(String message, String code) {
        super(message, "Invalid request format", code, null);
    }
    
    public InvalidRequestFormatException(Exception cause) {
        this("Invalid request format", "400", cause);
    }

    public InvalidRequestFormatException() {
        this("Invalid request format", "400", null);
    }

}
