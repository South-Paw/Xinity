package seng302.util.exceptions;

public class JsonMalformedException extends RuntimeException {

    public JsonMalformedException(String message) {
        super(message);
    }

    public JsonMalformedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
