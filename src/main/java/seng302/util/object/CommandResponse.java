package seng302.util.object;

import java.util.Map;

/**
 * A command response object.
 * Object wrapper for responding with a successful command output to the client.
 *
 * @author adg62
 */
public class CommandResponse {

    private String result;
    private Map<Object, Object> play;

    public CommandResponse(String message, Map<Object, Object> play) {
        this.result = message;
        this.play = play;
    }

    public String getResult() {
        return this.result;
    }

    public Map<Object, Object> getPlay() {
        return play;
    }
}
