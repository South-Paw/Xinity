package seng302.util.object;

/**
 * A login response object.
 * Object wrapper for responding to a login attempt by the client.
 *
 * @author adg62
 */
public class LoginResponse {
    private String success;

    public LoginResponse(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }
}
