package seng302.util.exceptions;

public class MethodErrorException extends RuntimeException {

    public MethodErrorException(String message) {
        super(message);
    }

    public MethodErrorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
