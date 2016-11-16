package seng302.util.object;

import seng302.util.ConfigUtil;

/**
 * A app response object.
 * Object wrapper for responding with the app information to the client.
 *
 * @author adg62
 */
public class AppResponse {

    private String version;

    public AppResponse() {
        version = ConfigUtil.retrieveVersion();
    }

    public String getVersion() {
        return version;
    }
}
