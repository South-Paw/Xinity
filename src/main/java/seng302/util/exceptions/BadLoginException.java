package seng302.util.exceptions;

public class BadLoginException extends RuntimeException {

    public BadLoginException(String message) {
        super(message);
    }

    public BadLoginException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
