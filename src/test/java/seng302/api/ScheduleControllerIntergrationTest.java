package seng302.api;

import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import seng302.App;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * API Integration tests for the Schedule Controller.
 *
 * @author adg62
 */
public class ScheduleControllerIntergrationTest {
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

    @SuppressWarnings("Duplicates")
    private TestResponse request(String method, String path, String jsonRequest) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);
            connection.setDoInput(true);

            if (jsonRequest != null) {
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(jsonRequest);
                writer.flush();
            }

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
    public void aValidApiKeyGetsTheirSchedules() {
        TestResponse response = request("GET", "/api/v1/" + testUserUuid + "/schedules", null);
        assertEquals(200, response.status);
        Map<String, Object> jsonResponse = response.json();

        List<Object> schedules = (List) jsonResponse.get("schedules");

        assertEquals(1.0, ((Map) schedules.get(0)).get("id"));
        assertEquals("Hard Schedule", ((Map) schedules.get(0)).get("name"));
        assertEquals(4.0, ((Map) schedules.get(0)).get("qnum"));

        assertEquals(2.0, ((Map) schedules.get(1)).get("id"));
        assertEquals("Easy Schedule", ((Map) schedules.get(1)).get("name"));
        assertEquals(9.0, ((Map) schedules.get(1)).get("qnum"));
    }

    @Test
    public void aValidApiKeyWithNoSchedulesStillSucceeds() {
        String testJson = "{\"username\": \"newUser1\", \"password\": \"apples\"}";

        // Register a new user
        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(200, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("success", jsonResponse.get("message"));

        // Login to get their api key
        TestResponse response1 = request("POST", "/api/v1/login", testJson);
        assertEquals(200, response1.status);
        Map<String, Object> jsonResponse1 = response1.json();
        String apikey = (String) jsonResponse1.get("success");

        // Get their schedules
        TestResponse response2 = request("GET", "/api/v1/" + apikey + "/schedules", null);
        assertEquals(200, response2.status);
        Map<String, Object> jsonResponse2 = response2.json();
        List<Object> schedules = (List) jsonResponse2.get("schedules");

        // We expect that there will be no schedules for this user
        assertEquals(0, schedules.size());
    }

    @Test
    public void anInvalidApiKeyFails() {
        TestResponse response = request("GET", "/api/v1/xyz/schedules", null);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("invalid", jsonResponse.get("message"));
    }
}
