package seng302.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author adg62, jps100
 */
public class ConfigUtilTest {
    @Test
    public void retrieveVersion() throws Exception {
        assertTrue(ConfigUtil.retrieveVersion().matches("[0-9]\\.[0-9]\\.[0-9]+"));
    }
}