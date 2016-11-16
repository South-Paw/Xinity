package seng302.util;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import seng302.App;
import spark.Spark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the database util class.
 *
 * @author adg62
 */
public class DatabaseUtilTest {
    private String testUserUuid = "testuuid";

    @BeforeClass
    public static void beforeClass() throws InterruptedException {
        App.main(new String[] {"-server", "-withtestdata"});

        Thread.sleep(5000);
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void aInvalidApiKeyReturnsFalse() {
        assertTrue(DatabaseUtil.validateApiKey(testUserUuid));
    }

    @Test
    public void aValidApiKeyReturnsTrue() {
        assertFalse(DatabaseUtil.validateApiKey("invalidkey"));
    }
}
