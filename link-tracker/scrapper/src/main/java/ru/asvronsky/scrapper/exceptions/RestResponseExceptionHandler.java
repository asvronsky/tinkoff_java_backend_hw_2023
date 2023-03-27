package ru.asvronsky.scrapper.exceptions;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity
            .status(status)
            .body(createError(ex, status.getReasonPhrase(), status.value()));
    }

    @ExceptionHandler(value = {InvalidRequestFormatException.class})
    public ResponseEntity<ApiErrorResponse> handleInvalidRequestFormatException(InvalidRequestFormatException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
            .status(status)
            .body(createError(ex, status.getReasonPhrase(), status.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleInternalError(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
            .status(status)
            .body(createError(ex, status.getReasonPhrase(), status.value()));
    }

    private ApiErrorResponse createError(Exception ex, String description, int code) {
        return createError(ex, description, Integer.toString(code));
    }

    private ApiErrorResponse createError(Exception ex, String description, String code) {
        return new ApiErrorResponse(
                description, 
                code, 
                ex.getClass().getSimpleName(), 
                ex.getMessage(),
                stackTraceToStrings(ex)
            );
    }

    private List<String> stackTraceToStrings(Exception ex) {
        return Arrays.stream(ex.getStackTrace())
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
