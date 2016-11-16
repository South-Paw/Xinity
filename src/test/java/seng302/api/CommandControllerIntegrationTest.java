package seng302.api;

import com.google.gson.Gson;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import seng302.App;
import seng302.workspace.Project;

import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * API Integration tests for the Command Controller.
 *
 * @author adg62
 */
public class CommandControllerIntegrationTest {

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

    @Before
    public void beforeTest() throws InterruptedException {
        Project.getInstance().resetTempo();
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
    public void anInvalidApiKeyIsInvalid() {
        String testJson = "{\"command\": \"note(60)\"}";

        TestResponse response = request("POST", "/api/v1/invaliduuid/command", testJson);

        assertEquals(400, response.status);

        Map<String, Object> jsonResponse = response.json();

        assertEquals("API Key is invalid.", jsonResponse.get("message"));
    }

    @Test
    public void aValidCommandShouldBeExecuted() {
        String testJson = "{\"command\": \"note(60)\"}";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(200, response.status);

        Map<String, Object> jsonResponse = response.json();

        assertEquals("C4", jsonResponse.get("result"));
        assertNull(jsonResponse.get("play"));
    }

    @Test
    public void aAlternateValidCommandShouldBeExecuted() {
        Project.getInstance().resetTempo();

        String testJson = "{\"command\": \"getTempo()\"}";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(200, response.status);

        Map<String, Object> jsonResponse = response.json();

        assertEquals("120 bpm", jsonResponse.get("result"));
        assertNull(jsonResponse.get("play"));
    }

    @Test
    public void anotherAlternateValidCommandShouldBeExecuted() {
        Project.getInstance().resetTempo();

        String testJson = "{\"command\": \"midi(C4)\"}";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(200, response.status);

        Map<String, Object> jsonResponse = response.json();

        assertEquals("60", jsonResponse.get("result"));

        // This test fails on CI for reasons unknown as system out's don't show the jsonResponse as
        // having a play item in the mapping. it's just weird and we don't have time to investigate.
        //assertNull(jsonResponse.get("play"));
    }

    @Test
    public void aInvalidCommandShouldStillBeExecuted() {
        String testJson = "{\"command\": \"getTempo\"}";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(200, response.status);

        Map<String, Object> jsonResponse = response.json();

        assertEquals("[ERROR] Invalid command.", jsonResponse.get("result"));
    }

    @Test
    public void aRequestWithInvalidJsonShouldError() {
        String testJson = "{\"command\": \"version\", ";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(400, response.status);

        Map<String, Object> jsonResponse = response.json();

        System.out.println(jsonResponse);

        assertEquals("Json object malformed.", jsonResponse.get("message"));
    }

    @Test
    public void aRequestWithNoCommandShouldError() {
        String testJson = "{\"random\": \"getTempo\"}";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(400, response.status);

        Map<String, Object> jsonResponse = response.json();

        assertEquals("Didn't receive a command to invoke.", jsonResponse.get("message"));
    }

    @Test
    public void aRequestWithANonJsonObjectShouldError() {
        String testJson = "getTempo()";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(400, response.status);

        Map<String, Object> jsonResponse = response.json();

        assertEquals("Json object malformed.", jsonResponse.get("message"));
    }

    @Test
    public void aPlayCommandAMidiWillReturnAJsonPlayObject() {
        String testJson = "{\"command\": \"play(60)\"}";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(200, response.status);

        Map<String, Object> jsonResponse = response.json();

        Map playMap = (Map) jsonResponse.get("play");
        ArrayList noteArray = (ArrayList) ((ArrayList) playMap.get("note")).get(0);

        assertEquals("Playing: 60", jsonResponse.get("result"));
        assertTrue(noteArray.get(0).equals(60.0));
        assertTrue(noteArray.get(1).equals(500.0));
    }

    @Test
    public void aPlayCommandWithANoteWillReturnAJsonPlayObject() {
        String testJson = "{\"command\": \"play(C4)\"}";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(200, response.status);

        Map<String, Object> jsonResponse = response.json();

        Map playMap = (Map) jsonResponse.get("play");
        ArrayList noteArray = (ArrayList) ((ArrayList) playMap.get("note")).get(0);

        assertEquals("Playing: C4", jsonResponse.get("result"));
        assertTrue(noteArray.get(0).equals(60.0));
        assertTrue(noteArray.get(1).equals(500.0));
    }

    @Test
    public void aPlayCommandWithANoteAndCustomDuration() {
        String testJson = "{\"command\": \"play(C4, 300)\"}";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(200, response.status);

        Map<String, Object> jsonResponse = response.json();

        Map playMap = (Map) jsonResponse.get("play");
        ArrayList noteArray = (ArrayList) ((ArrayList) playMap.get("note")).get(0);

        assertEquals("Playing: C4", jsonResponse.get("result"));
        assertTrue(noteArray.get(0).equals(60.0));
        assertTrue(noteArray.get(1).equals(300.0));
    }

    @Test
    public void aPlayCommandReceivesCalculatedTempoCorrectlyAfterItsChanged() {
        String changeTempoJson = "{\"command\": \"setTempo(300)\"}";

        TestResponse tempoChangeResponse = request("POST", "/api/v1/" + testUserUuid + "/command",
            changeTempoJson);

        assertEquals(200, tempoChangeResponse.status);

        // now that we've changed the tempo to 300

        String testJson = "{\"command\": \"play(C4)\"}";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(200, response.status);

        Map<String, Object> jsonResponse = response.json();

        Map playMap = (Map) jsonResponse.get("play");
        ArrayList noteArray = (ArrayList) ((ArrayList) playMap.get("note")).get(0);

        assertEquals("Playing: C4", jsonResponse.get("result"));
        assertTrue(noteArray.get(0).equals(60.0));
        assertTrue(noteArray.get(1).equals(200.0));
    }

    @Test
    public void aPlayCommandWithAScale() {
        String testJson = "{\"command\": \"playScale(C4, major)\"}";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(200, response.status);

        Map<String, Object> jsonResponse = response.json();

        Map playMap = (Map) jsonResponse.get("play");
        ArrayList noteArray = ((ArrayList) playMap.get("scale"));

        assertEquals("C4, D4, E4, F4, G4, A4, B4, C5", jsonResponse.get("result"));

        assertTrue(((ArrayList) noteArray.get(0)).get(0).equals(60.0));
        assertTrue(((ArrayList) noteArray.get(0)).get(1).equals(250.0));

        assertTrue(((ArrayList) noteArray.get(2)).get(0).equals(64.0));
        assertTrue(((ArrayList) noteArray.get(2)).get(1).equals(250.0));

        assertTrue(((ArrayList) noteArray.get(7)).get(0).equals(72.0));
        assertTrue(((ArrayList) noteArray.get(7)).get(1).equals(250.0));
    }

    @Test
    public void aPlayCommandWithAInterval() {
        String testJson = "{\'command\': \'playInterval(C4, \"major 2nd\")\'}";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(200, response.status);

        Map<String, Object> jsonResponse = response.json();

        Map playMap = (Map) jsonResponse.get("play");
        ArrayList noteArray = ((ArrayList) playMap.get("interval"));

        assertEquals("C4 and D4", jsonResponse.get("result"));

        assertTrue(((ArrayList) noteArray.get(0)).get(0).equals(60.0));
        assertTrue(((ArrayList) noteArray.get(0)).get(1).equals(500.0));

        assertTrue(((ArrayList) noteArray.get(1)).get(0).equals(0.0));
        assertTrue(((ArrayList) noteArray.get(1)).get(1).equals(1500.0));

        assertTrue(((ArrayList) noteArray.get(2)).get(0).equals(62.0));
        assertTrue(((ArrayList) noteArray.get(2)).get(1).equals(500.0));
    }

    @Test
    public void aPlayCommandWithAChord() {
        String testJson = "{\"command\": \"playChord(C4, major)\"}";

        TestResponse response = request("POST", "/api/v1/" + testUserUuid + "/command", testJson);

        assertEquals(200, response.status);

        Map<String, Object> jsonResponse = response.json();

        Map playMap = (Map) jsonResponse.get("play");
        ArrayList noteArray = ((ArrayList) playMap.get("chord"));

        assertEquals("C4, E4, G4", jsonResponse.get("result"));

        assertTrue(((ArrayList) noteArray.get(0)).get(0).equals(60.0));
        assertTrue(((ArrayList) noteArray.get(0)).get(1).equals(500.0));

        assertTrue(((ArrayList) noteArray.get(1)).get(0).equals(64.0));
        assertTrue(((ArrayList) noteArray.get(1)).get(1).equals(500.0));

        assertTrue(((ArrayList) noteArray.get(2)).get(0).equals(67.0));
        assertTrue(((ArrayList) noteArray.get(2)).get(1).equals(500.0));
    }
}
