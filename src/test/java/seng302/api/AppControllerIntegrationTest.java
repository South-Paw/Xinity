package seng302.api;

import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import seng302.App;
import seng302.util.ConfigUtil;

import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * API Integration tests for the Command Controller.
 *
 * @author adg62
 */
public class AppControllerIntegrationTest {

    @BeforeClass
    public static void beforeClass() throws InterruptedException {
        App.main(new String[] {"-server", "-withtestdata"});

        Thread.sleep(5000);
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    private static class TestResponse {
        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String, Object> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }

    private TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method);

            connection.setDoOutput(true);

            connection.connect();

            String body;

            if (connection.getResponseCode() == 200) {
                body = IOUtils.toString(connection.getInputStream());
            } else {
                body = IOUtils.toString(connection.getErrorStream());
            }

            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    @Test
    public void aRequestShouldGetTheVersion() {
        TestResponse response = request("GET", "/api/v1/app");

        assertEquals(200, response.status);

        Map<String, Object> jsonResponse = response.json();

        assertEquals(ConfigUtil.retrieveVersion(), jsonResponse.get("version"));
    }
}
