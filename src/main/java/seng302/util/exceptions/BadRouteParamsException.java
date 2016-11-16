package seng302.util.exceptions;

public class BadRouteParamsException extends RuntimeException {

    public BadRouteParamsException(String message) {
        super(message);
    }

    public BadRouteParamsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
