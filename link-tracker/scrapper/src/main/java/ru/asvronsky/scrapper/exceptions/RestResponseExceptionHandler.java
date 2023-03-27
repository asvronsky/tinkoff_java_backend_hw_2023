package ru.asvronsky.scrapper.exceptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private final Map<Class<?>, HttpStatus> exceptionToStatusMap;
    {
        exceptionToStatusMap = new HashMap<>();
        exceptionToStatusMap.put(InvalidRequestFormatException.class, HttpStatus.BAD_REQUEST);
        exceptionToStatusMap.put(ChatIdNotFoundException.class, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(value = {RestResponseException.class})
    public ResponseEntity<ApiErrorResponse> handleRestResponseException(RestResponseException ex, WebRequest request) {

        return new ResponseEntity<>(mapRestResponseException(ex), exceptionToStatusMap.get(ex.getClass()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleInternalError(Exception ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse(
                    "Internal server error", 
                    "500", 
                    ex.getClass().getSimpleName(), 
                    ex.getLocalizedMessage(),
                    stackTraceToStrings(ex)
                ), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
    }

    private ApiErrorResponse mapRestResponseException(RestResponseException ex) {
        return new ApiErrorResponse(
                ex.getDescription(), 
                ex.getCode(), 
                ex.getClass().getSimpleName(), 
                ex.getMessage(),
                stackTraceToStrings(ex)
            );
    }

    private List<String> stackTraceToStrings(Exception exception) {
        return Arrays.stream(exception.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();
    }

    private record ApiErrorResponse(
        String description,
        String code,
        String exceptionName,
        String exceptionMessage,
        List<String> stacktrace
    ) 
    { };
}
