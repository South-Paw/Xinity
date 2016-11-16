package seng302.util.exceptions;

public class BadRegistrationException extends RuntimeException {

    public BadRegistrationException(String message) {
        super(message);
    }

    public BadRegistrationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
