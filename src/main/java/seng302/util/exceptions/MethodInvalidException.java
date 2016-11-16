package seng302.util.exceptions;

public class MethodInvalidException extends RuntimeException {

    public MethodInvalidException(String message) {
        super(message);
    }

    public MethodInvalidException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
