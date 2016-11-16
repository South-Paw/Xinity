package seng302.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Version Utilities.
 * Retrieve various properties from the config.properties file.
 *
 * @author adg62, jps100
 */
public final class ConfigUtil {
    private static Properties properties;

    /** Private Constructor. */
    private ConfigUtil() {}

    private static void loadProperties() {
        if (properties == null) {
            properties = new Properties();
        }

        try {
            properties.load(InputStream.class.getResourceAsStream("/config.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the application name.
     *
     * @return The application name.
     */
    public static String retrieveAppName() {
        loadProperties();

        return properties.getProperty("AppName");
    }

    /**
     * Retrieves the application version.
     *
     * @return The application version.
     */
    public static String retrieveVersion() {
        loadProperties();

        return properties.getProperty("Version");
    }
}
