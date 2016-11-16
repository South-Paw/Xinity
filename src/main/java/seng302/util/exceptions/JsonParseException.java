package seng302.util.exceptions;

public class JsonParseException extends RuntimeException {

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
