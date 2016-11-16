package seng302.api;

import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import seng302.App;
import seng302.util.DatabaseUtil;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * API Integration tests for the User Controller.
 *
 * @author adg62
 */
public class UserControllerIntergrationTest {
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

    // login api tests
    @Test
    public void aValidUserCanLoginAGetsTheirApiKey() {
        String testJson = "{\"username\": \"test\", \"password\": \"apples\"}";

        TestResponse response = request("POST", "/api/v1/login", testJson);
        assertEquals(200, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals(testUserUuid, jsonResponse.get("success"));
    }

    @Test
    public void aInvalidUserGetsErrorMessage() {
        String testJson = "{\"username\": \"notanaccount\", \"password\": \"password\"}";

        TestResponse response = request("POST", "/api/v1/login", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Invalid username or password.", jsonResponse.get("message"));
    }

    @Test
    public void aLoginWithAUsernameTooShortErrors() {
        String testJson = "{\"username\": \"a\", \"password\": \"totallyvalidpassword\"}";

        TestResponse response = request("POST", "/api/v1/login", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Username must be at least 3 characters.", jsonResponse.get("message"));
    }

    @Test
    public void aLoginWithAPasswordTooShortErrors() {
        String testJson = "{\"username\": \"totallyvalidusernam\", \"password\": \"e\"}";

        TestResponse response = request("POST", "/api/v1/login", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Password must be at least 5 characters.", jsonResponse.get("message"));
    }

    @Test
    public void aLoginWithoutAUsernameErrors() {
        String testJson = "{\"password\": \"helloworld\"}";

        TestResponse response = request("POST", "/api/v1/login", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Username is required.", jsonResponse.get("message"));
    }

    @Test
    public void aLoginWithoutAPasswordErrors() {
        String testJson = "{\"username\": \"helloworld\"}";

        TestResponse response = request("POST", "/api/v1/login", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Password is required.", jsonResponse.get("message"));
    }

    @Test
    public void aLoginWithInvalidJsonObjectErrors() {
        String testJson = "{\"username\": \"helloworld\", \"password\": \"imatree\"";

        TestResponse response = request("POST", "/api/v1/login", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Json object malformed.", jsonResponse.get("message"));
    }

    // register api tests
    @Test
    public void aValidRegistrationObjectCreatesANewUser() {
        String testJson = "{\"username\":\"JohnCena\",\"password\":\"dunDunadun\"}";

        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(200, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("success", jsonResponse.get("message"));
    }

    @Test
    public void aUsernameCannotMatchPassword() {
        String testJson = "{\"username\":\"liamiscool\",\"password\":\"liamiscool\"}";

        TestResponse registrationOne = request("POST", "/api/v1/register", testJson);

        assertEquals(400, registrationOne.status);
        Map<String, Object> jsonResponse1 = registrationOne.json();
        assertEquals("Username cannot be same as password!", jsonResponse1.get("message"));
    }

    @Test
    public void aUserCannotBeRegisteredTwice() {
        String testJson = "{\"username\":\"TotallyNotJohnCena\",\"password\":\"dunDunadun\"}";

        TestResponse registrationOne = request("POST", "/api/v1/register", testJson);

        assertEquals(200, registrationOne.status);
        Map<String, Object> jsonResponse1 = registrationOne.json();
        assertEquals("success", jsonResponse1.get("message"));

        TestResponse registrationTwo = request("POST", "/api/v1/register", testJson);

        assertEquals(400, registrationTwo.status);
        Map<String, Object> jsonResponse2 = registrationTwo.json();
        assertEquals("That username is already in use.", jsonResponse2.get("message"));
    }

    @Test
    public void aInvalidRegistrationObjectErrors() {
        String testJson = "{\"JohnCena\": \"maPassw0rd\"}";

        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Username is required.", jsonResponse.get("message"));
    }

    @Test
    public void aUsernameWithSpacesIsInvalid() {
        String testJson = "{\"username\":\"John Cena\",\"password\":\"maPassw0rd\"}";

        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Username cannot contain spaces.", jsonResponse.get("message"));
    }

    @Test
    public void aPasswordWithSpacesIsInvalid() {
        String testJson = "{\"username\":\"JohnCena\",\"password\":\"ma Passw0rd\"}";

        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Password cannot contain spaces.", jsonResponse.get("message"));
    }

    @Test
    public void optionalParamsAreAcceptedOnUserRegistration() {
        String testJson = "{\"username\":\"cenalicious\",\"password\":\"dankmemes\", \"name\": "
            + "\"John Cena\", \"email\": \"totallynotcena@john.net\"}";

        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(200, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("success", jsonResponse.get("message"));
    }

    @Test
    public void aRegistrationWithInvalidJsonObjectErrors() {
        String testJson = "{\"username\":\"cenalicious\",\"pass";

        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Json object malformed.", jsonResponse.get("message"));
    }

    @Test
    public void aRegistrationWithALongUsernameFails() {
        String testJson = "{\"username\":\"reallyreallyreallylongusernametotry\", "
            + "\"password\":\"l3g1tgangstar\"}";

        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Username can't be longer than 20 characters.", jsonResponse.get("message"));
    }

    @Test
    public void aRegistrationWithALongNameFails() {
        String testJson = "{\"username\":\"legitname\", \"password\":\"l3g1tgangstar\", "
            + "\"name\": \"reallyreallyreallylongnametotry\"}";

        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Name cannot be more than 30 characters.", jsonResponse.get("message"));
    }

    @Test
    public void aRegistrationWithALongEmailFails() {
        String testJson = "{\"username\":\"legitname\", \"password\":\"l3g1tgangstar\", "
            + "\"email\": \"reallyreallyreallylongnametotry@lol.com\"}";

        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Email cannot be more than 30 characters.", jsonResponse.get("message"));
    }

    @Test
    public void aRegistrationResultsInUserCreation() {
        String testJson = "{\"username\":\"AnotherJohnCena\",\"password\":\"dunDunadun\"}";

        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(200, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("success", jsonResponse.get("message"));

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                Statement statement = connection.createStatement();
                statement.setQueryTimeout(10);

                ResultSet userLookup = statement.executeQuery(
                    "select count(*) as 'count' from users where username='JohnCena'");

                if (userLookup.getInt("count") != 1) {
                    fail();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void aRegistrationResultsInUserDetailsCreation() {
        String testJson = "{\"username\":\"PotatoMash\",\"password\":\"andGravy\"}";

        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(200, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("success", jsonResponse.get("message"));

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                PreparedStatement getUserId = connection.prepareStatement(
                    "select id from users where username=?;");

                getUserId.setString(1, "PotatoMash");
                getUserId.setQueryTimeout(10);

                ResultSet userIdRs = getUserId.executeQuery();

                Integer userId = userIdRs.getInt("id");

                PreparedStatement getDetailsCount = connection.prepareStatement(
                    "select count(*) as 'count' from user_details where id=?;");

                getDetailsCount.setInt(1, userId);
                getDetailsCount.setQueryTimeout(10);

                ResultSet userDetailsCount = getDetailsCount.executeQuery();

                Integer count = userDetailsCount.getInt("count");

                if (count != 1) {
                    fail();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // reset api key tests
    @Test
    public void aValidApiKeyResets() {
        String testJson = "{\"username\": \"randomodnar\", \"password\": \"apples\"}";

        // Register a new user
        TestResponse response = request("POST", "/api/v1/register", testJson);
        assertEquals(200, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("success", jsonResponse.get("message"));

        // Login to get their api key
        TestResponse response1 = request("POST", "/api/v1/login", testJson);
        assertEquals(200, response1.status);
        Map<String, Object> jsonResponse1 = response1.json();
        String apikey1 = (String) jsonResponse1.get("success");

        // Now reset the api key
        TestResponse response2 = request("POST", "/api/v1/" + apikey1 + "/resetApiKey", null);
        assertEquals(200, response2.status);
        Map<String, Object> jsonResponse2 = response2.json();
        assertEquals("done", jsonResponse2.get("message"));

        // Now attempt another login with same user
        TestResponse response3 = request("POST", "/api/v1/login", testJson);
        assertEquals(200, response3.status);
        Map<String, Object> jsonResponse3 = response3.json();
        String apikey2 = (String) jsonResponse3.get("success");

        // API key 1 and 2 should be different
        assertNotEquals(apikey1, apikey2);
    }

    @Test
    public void aInvalidApiKeyDoesNotReset() {
        // Now reset the api key
        TestResponse response2 = request("POST", "/api/v1/xyz/resetApiKey", null);
        assertEquals(400, response2.status);
        Map<String, Object> jsonResponse2 = response2.json();
        assertEquals("invalid", jsonResponse2.get("message"));
    }

    // validate api tests
    @Test
    public void aValidApiKeyReturnsAsValid() {
        String testJson = "{\"username\": \"randomodnar1\", \"password\": \"apples\"}";

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

        // Now validate the key
        TestResponse response2 = request("GET", "/api/v1/" + apikey + "/validate", null);
        assertEquals(200, response2.status);
        Map<String, Object> jsonResponse2 = response2.json();
        assertEquals("valid", jsonResponse2.get("message"));
    }

    @Test
    public void aInvalidApiKeyReturnsAsInvalid() {
        TestResponse response = request("GET", "/api/v1/notarealkey/validate", null);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("invalid", jsonResponse.get("message"));
    }

    // get user api tests
    @Test
    public void aValidApiKeyGetsItsUserProfile() {
        String testJson = "{\"username\": \"randomodnar2\", \"password\": \"apples\", \"name\": "
            + "\"john\"}";

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

        // Now get their user profile
        TestResponse response2 = request("GET", "/api/v1/" + apikey + "/user", null);
        assertEquals(200, response2.status);
        Map<String, Object> jsonResponse2 = response2.json();
        assertEquals("john", jsonResponse2.get("name"));
    }

    @Test
    public void aInvalidApiKeyReturnsAnError() {
        TestResponse response = request("GET", "/api/v1/xyz/user", null);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("API Key is invalid.", jsonResponse.get("message"));
    }

    // post user api tests
    @Test
    public void aValidApiKeyAndUserObjectUpdatesThatUser() {
        String testJson = "{\"userId\":1,\"name\":\"Mr Tester\","
            + "\"email\":\"hello@iliketests.co.nz\",\"location\":\"New Zealand\","
            + "\"bio\":\"I like to test '';;\",\"imgUrl\":\"unknown\",\"theme\":\"dark\","
            + "\"tempo\":120}";

        TestResponse response = request("POST", "/api/v1/testuuid/user", testJson);
        assertEquals(200, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("done", jsonResponse.get("message"));
    }

    @Test
    public void aInvalidApiKeyAndUserObjectReturnsError() {
        String testJson = "{\"userId\":1,";

        TestResponse response = request("POST", "/api/v1/xyz/user", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("API Key is invalid.", jsonResponse.get("message"));
    }

    @Test
    public void aValidApiKeyAndInvalidUserObjectReturnsError() {
        String testJson = "{\"userId\":1,";

        TestResponse response = request("POST", "/api/v1/testuuid/user", testJson);
        assertEquals(400, response.status);
        Map<String, Object> jsonResponse = response.json();
        assertEquals("Json object malformed.", jsonResponse.get("message"));
    }
}
