package seng302.util.object;

/**
 * A general response object.
 * Object wrapper for responding with a message to the client.
 *
 * @author adg62
 */
public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
