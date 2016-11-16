package seng302.util.exceptions;

public class ApiKeyInvalidException extends RuntimeException {

    public ApiKeyInvalidException(String message) {
        super(message);
    }

    public ApiKeyInvalidException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
