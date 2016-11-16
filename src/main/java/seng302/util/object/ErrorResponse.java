package seng302.util.object;

/**
 * A error response object.
 * Object wrapper for responding with an error to the client.
 *
 * @author adg62
 */
public class ErrorResponse {

    private String message;

    public ErrorResponse(String message, String... args) {
        this.message = String.format(message, args);
    }

    public ErrorResponse(Exception exception) {
        this.message = exception.getMessage();
    }

    public String getMessage() {
        return this.message;
    }
}
