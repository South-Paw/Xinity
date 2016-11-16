package seng302.api.services;

import seng302.api.controllers.UserController;
import seng302.util.DatabaseUtil;
import seng302.util.JsonUtil;
import seng302.util.exceptions.ApiKeyInvalidException;
import seng302.util.exceptions.BadLoginException;
import seng302.util.exceptions.BadRegistrationException;
import seng302.util.exceptions.DatabaseException;
import seng302.util.exceptions.JsonMalformedException;
import seng302.util.exceptions.JsonParseException;
import seng302.util.object.LoginResponse;
import seng302.util.object.MessageResponse;
import seng302.util.object.UserResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

/**
 * The user services class. Manages methods to do with user actions such as log ins, registrations,
 * profile updates and more.
 *
 * @author adg62
 */
public class UserService {

    /**
     * Handles login requests, returns the users API key to them when successful login.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param body A json string containing a username and password.
     * @return A valid API key for the user if they log in or an exception if there's an issue.
     */
    public LoginResponse postLogin(String ip, String url, String body) {
        System.out.println("Request by: " + ip + "\nOn route: POST " +  url);
        System.out.println("Body: " + body);

        // check json is valid
        Map<String, Object> jsonMap;

        try {
            jsonMap = JsonUtil.fromJson(body);
        } catch (Exception e) {
            throw new JsonMalformedException("Json object malformed.");
        }

        // should have a 'username' and 'password' object
        if (jsonMap.get("username") == null) {
            throw new JsonParseException("Username is required.");
        } else if (jsonMap.get("password") == null) {
            throw new JsonParseException("Password is required.");
        }

        // should be the correct length
        if (((String) jsonMap.get("username")).length() < 3) {
            throw new JsonParseException("Username must be at least 3 characters.");
        } else if (((String) jsonMap.get("password")).length() < 5) {
            throw new JsonParseException("Password must be at least 5 characters.");
        }

        // if we haven't thrown an exception, then they're valid
        String username = (String) jsonMap.get("username");
        String password = (String) jsonMap.get("password");

        String apiKey = null;

        // query the database for the username
        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                PreparedStatement countValidUsers = connection.prepareStatement(
                    "select count(*) as 'count' from users where username=? and password=?;");

                countValidUsers.setString(1, username);
                countValidUsers.setString(2, password);
                countValidUsers.setQueryTimeout(10);

                ResultSet userLookup = countValidUsers.executeQuery();

                if (userLookup.getInt("count") != 1) {
                    throw new BadLoginException("Invalid username or password.");
                } else {
                    PreparedStatement getValidUsers = connection.prepareStatement(
                        "select * from users where username=? and password=?;");

                    getValidUsers.setString(1, username);
                    getValidUsers.setString(2, password);
                    getValidUsers.setQueryTimeout(10);

                    ResultSet userData = getValidUsers.executeQuery();

                    apiKey = userData.getString("apikey");
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

        if (apiKey == null) {
            throw new DatabaseException("API key not found for this user.");
        }

        LoginResponse loginResponse = new LoginResponse(apiKey);

        System.out.println("Responding with: " + JsonUtil.toJson(loginResponse));
        System.out.println(UserController.lineBreak);

        return loginResponse;
    }

    /**
     * Handles registration requests.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param body A json string containing a username and password and optional extras.
     * @return A 201 OK header.
     */
    public MessageResponse postRegistration(String ip, String url, String body) {
        System.out.println("Request by: " + ip + "\nOn route: POST " +  url);
        System.out.println("Body: " + body);

        // check json is valid
        Map<String, Object> jsonMap;

        try {
            jsonMap = JsonUtil.fromJson(body);
        } catch (Exception e) {
            throw new JsonMalformedException("Json object malformed.");
        }

        // should have a 'username' and 'password' object
        if (jsonMap.get("username") == null) {
            throw new JsonParseException("Username is required.");
        } else if (jsonMap.get("password") == null) {
            throw new JsonParseException("Password is required.");
        }

        // should be the correct length
        if (((String) jsonMap.get("username")).length() < 3) {
            throw new JsonParseException("Username must be at least 3 characters.");
        } else if (((String) jsonMap.get("username")).length() > 20) {
            throw new JsonParseException("Username can't be longer than 20 characters.");
        } else if (((String) jsonMap.get("password")).length() < 5) {
            throw new JsonParseException("Password must be at least 5 characters.");
        }

        // username can't be same as password
        if ((jsonMap.get("username")).equals(jsonMap.get("password"))) {
            throw new JsonParseException("Username cannot be same as password!");
        }

        // no spaces shall pass!
        if (((String) jsonMap.get("username")).contains(" ")) {
            throw new JsonParseException("Username cannot contain spaces.");
        } else if (((String) jsonMap.get("password")).contains(" ")) {
            throw new JsonParseException("Password cannot contain spaces.");
        }

        // and we'll check that if the other two are set, that they aren't too massive...
        // other than that, we don't really care about validity... because not really required
        if (jsonMap.get("name") != null) {
            if (((String) jsonMap.get("name")).length() > 30) {
                throw new JsonParseException("Name cannot be more than 30 characters.");
            }
        } else if (jsonMap.get("email") != null) {
            if (((String) jsonMap.get("email")).length() > 30) {
                throw new JsonParseException("Email cannot be more than 30 characters.");
            }
        }

        // grab all the data from the json request
        String username = (String) jsonMap.get("username");
        String password = (String) jsonMap.get("password");
        String name = null;
        String email = null;
        String uuid = UUID.randomUUID().toString().replace("-", "");

        if (jsonMap.get("name") != null) {
            name = (String) jsonMap.get("name");
        }

        if (jsonMap.get("email") != null) {
            email = (String) jsonMap.get("email");
        }

        // check that the username is unique before we register the user
        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                PreparedStatement checkIfUserIsUnique = connection.prepareStatement(
                    "select count(*) as 'count' from users where username=?;");

                checkIfUserIsUnique.setString(1, username);
                checkIfUserIsUnique.setQueryTimeout(10);

                ResultSet userLookup = checkIfUserIsUnique.executeQuery();

                if (userLookup.getInt("count") > 0) {
                    throw new BadRegistrationException("That username is already in use.");
                } else {
                    PreparedStatement createNewUser = connection.prepareStatement(
                        "insert into users (username, password, apikey) values (?, ?, ?);");

                    createNewUser.setString(1, username);
                    createNewUser.setString(2, password);
                    createNewUser.setString(3, uuid);
                    createNewUser.setQueryTimeout(10);

                    createNewUser.executeUpdate();

                    // get the id for the new user
                    PreparedStatement getNewUser = connection.prepareStatement(
                        "select id from users where username=?;");

                    getNewUser.setString(1, username);

                    // create a details row for this user
                    String newUserQueryLine1 = "insert into `user_details` (`id`";
                    String newUserQueryLine2 = ") values (?";

                    if (name != null) {
                        newUserQueryLine1 += ", name";
                        newUserQueryLine2 += ", ?";
                    }

                    if (email != null) {
                        newUserQueryLine1 += ", email";
                        newUserQueryLine2 += ", ?";
                    }

                    PreparedStatement addNewUserDetails = connection.prepareStatement(
                        newUserQueryLine1 + newUserQueryLine2 + ");");

                    Integer pointer = 1;

                    ResultSet newUsersData = getNewUser.executeQuery();
                    Integer newUserId = newUsersData.getInt("id");

                    addNewUserDetails.setInt(pointer++, newUserId);

                    if (name != null) {
                        addNewUserDetails.setString(pointer++, name);
                    }

                    if (email != null) {
                        addNewUserDetails.setString(pointer++, email);
                    }

                    addNewUserDetails.executeUpdate();
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

        MessageResponse messageResponse = new MessageResponse("success");

        System.out.println("Responding with: " + JsonUtil.toJson(messageResponse));
        System.out.println(UserController.lineBreak);

        return messageResponse;
    }

    /**
     * Method for resetting a users API key.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param routeSplat The route splat.
     * @return A validation response containing "done" or an exception.
     */
    public MessageResponse postResetApiKey(String ip, String url, String[] routeSplat) {
        System.out.println("Request by: " + ip + "\nOn route: POST " +  url);
        String thisApiKey = routeSplat[0];

        Boolean isValid = DatabaseUtil.validateApiKey(thisApiKey);

        if (!isValid) {
            throw new ApiKeyInvalidException("invalid");
        }

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                PreparedStatement apiKeyLookup = connection.prepareStatement(
                    "select * from users where apikey=?;");

                apiKeyLookup.setString(1, thisApiKey);
                apiKeyLookup.setQueryTimeout(10);

                ResultSet userApiKeyLookup = apiKeyLookup.executeQuery();

                // get the id of this user
                Integer thisUsersId = userApiKeyLookup.getInt("id");

                // create a new api key
                String uuid = UUID.randomUUID().toString().replace("-", "");

                // update the database with it
                PreparedStatement apiKeyReplacement = connection.prepareStatement(
                    "update `users` set `apikey`=? where `id`=?;");

                apiKeyReplacement.setString(1, uuid);
                apiKeyReplacement.setInt(2, thisUsersId);
                apiKeyReplacement.setQueryTimeout(10);

                apiKeyReplacement.executeUpdate();

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

        MessageResponse messageResponse = new MessageResponse("done");

        System.out.println("Responding with: " + JsonUtil.toJson(messageResponse));
        System.out.println(UserController.lineBreak);

        return messageResponse;
    }

    /**
     * Handles checking if an API key is valid.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param routeSplat The route splat.
     * @return A validation response or an exception.
     */
    public MessageResponse getValidation(String ip, String url, String[] routeSplat) {
        System.out.println("Request by: " + ip + "\nOn route: GET " +  url);

        Boolean isValid = DatabaseUtil.validateApiKey(routeSplat[0]);

        if (!isValid) {
            throw new ApiKeyInvalidException("invalid");
        }

        MessageResponse messageResponse = new MessageResponse("valid");

        System.out.println("Responding with: " + JsonUtil.toJson(messageResponse));
        System.out.println(UserController.lineBreak);

        return messageResponse;
    }

    /**
     * Handles retrieving a user profile from the database.
     * Currently only allows for you to retrieve your own profile - could easily be extended to
     * do other users profiles though.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param routeSplat An API key of the user.
     * @return The requested profile.
     */
    public UserResponse getProfile(String ip, String url, String[] routeSplat) {
        System.out.println("Request by: " + ip + "\nOn route: GET " +  url);

        String apiKey = routeSplat[0];

        // verify api key
        Boolean isApiKeyValid = DatabaseUtil.validateApiKey(apiKey);

        if (!isApiKeyValid) {
            throw new ApiKeyInvalidException("API Key is invalid.");
        }

        UserResponse userResponse = null;

        // do sql things here
        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                PreparedStatement getUserByApiKey = connection.prepareStatement(
                    "select * from users where apikey=?;");

                getUserByApiKey.setString(1, apiKey);
                getUserByApiKey.setQueryTimeout(10);

                ResultSet thisUser = getUserByApiKey.executeQuery();

                Integer userId = thisUser.getInt("id");
                String username = thisUser.getString("username");

                PreparedStatement getThisUsersDetails = connection.prepareStatement(
                    "select * from user_details where id=?;");

                getThisUsersDetails.setInt(1, userId);
                getThisUsersDetails.setQueryTimeout(10);

                ResultSet thisUsersDetails = getThisUsersDetails.executeQuery();

                String name = thisUsersDetails.getString("name");
                String email = thisUsersDetails.getString("email");
                String location = thisUsersDetails.getString("location");
                String bio = thisUsersDetails.getString("bio");
                String imgUrl = thisUsersDetails.getString("imgurl");
                String theme = thisUsersDetails.getString("theme");
                Integer tempo = thisUsersDetails.getInt("tempo");

                userResponse = new UserResponse(userId, username, name, email, location, bio,
                    imgUrl, theme, tempo);

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

        if (userResponse == null) {
            throw new DatabaseException("Didn't find a user for this api key.");
        }

        System.out.println("Responding with: " + JsonUtil.toJson(userResponse));
        System.out.println(UserController.lineBreak);

        return userResponse;
    }

    /**
     * Update the user profile route.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param routeSplat An API key of the user.
     * @param body The request body.
     * @return A json object containing "done" if successful, otherwise an exception.
     */
    public MessageResponse postProfile(String ip, String url, String[] routeSplat, String body) {
        System.out.println("Request by: " + ip + "\nOn route: POST " + url);
        System.out.println("Body: " + body);

        String apiKey = routeSplat[0];

        // verify api key
        Boolean isApiKeyValid = DatabaseUtil.validateApiKey(apiKey);

        if (!isApiKeyValid) {
            throw new ApiKeyInvalidException("API Key is invalid.");
        }

        // check json is valid
        Map<String, Object> jsonMap;

        try {
            jsonMap = JsonUtil.fromJson(body);
        } catch (Exception e) {
            throw new JsonMalformedException("Json object malformed.");
        }

        String name = null;
        String email = null;
        String location = null;

        if (jsonMap.get("name") != null) {
            name = (String) jsonMap.get("name");
        }

        if (jsonMap.get("email") != null) {
            email = (String) jsonMap.get("email");
        }

        if (jsonMap.get("location") != null) {
            location = (String) jsonMap.get("location");
        }

        String bio = null;
        String img = null;
        Integer tempo = null;
        String theme = null;

        if (jsonMap.get("bio") != null) {
            bio = (String) jsonMap.get("bio");
        }

        if (jsonMap.get("imgUrl") != null) {
            img = (String) jsonMap.get("imgUrl");
        }

        if (jsonMap.get("tempo") != null) {
            tempo = ((Double) jsonMap.get("tempo")).intValue();
        }

        if (jsonMap.get("theme") != null) {
            theme = (String) jsonMap.get("theme");
        }

        String imgUrl;

        switch (img) {
            case "1":
                imgUrl = "1";
                break;
            case "2":
                imgUrl = "2";
                break;
            case "3":
                imgUrl = "3";
                break;
            case "4":
                imgUrl = "4";
                break;
            case "5":
                imgUrl = "5";
                break;
            default:
                imgUrl = "unknown";
                break;
        }

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(
                    DatabaseUtil.getFullConnectionString());

                // create a details row for this user
                String query = "update `user_details` set ";

                if (name != null) {
                    query += "`name`=?, ";
                }

                if (email != null) {
                    query += "`email`=?, ";
                }

                if (location != null) {
                    query += "`location`=?, ";
                }

                if (bio != null) {
                    query += "`bio`=?, ";
                }

                query += "`imgurl`=?, ";

                if (tempo != null) {
                    query += "`tempo`=?, ";
                }

                if (theme != null) {
                    query += "`theme`=?, ";
                }

                PreparedStatement getUserId = connection.prepareStatement(
                    "select * from users where apikey=?;");

                getUserId.setString(1, apiKey);
                getUserId.setQueryTimeout(10);

                ResultSet thisUser = getUserId.executeQuery();

                Integer thisUsersId = thisUser.getInt("id");

                // Finish building the query
                query = query.substring(0, query.length() - 2)
                    + " where `id`=" + thisUsersId + ";";

                // prepare sql code
                PreparedStatement updateUser = connection.prepareStatement(query);

                Integer pointer = 1;

                if (name != null) {
                    updateUser.setString(pointer++, name);
                }

                if (email != null) {
                    updateUser.setString(pointer++, email);
                }

                if (location != null) {
                    updateUser.setString(pointer++, location);
                }

                if (bio != null) {
                    updateUser.setString(pointer++, bio);
                }

                updateUser.setString(pointer++, imgUrl);

                if (tempo != null) {
                    updateUser.setInt(pointer++, tempo);
                }

                if (theme != null) {
                    updateUser.setString(pointer++, theme);
                }

                updateUser.setQueryTimeout(10);

                updateUser.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DatabaseException("Database Exception!");
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new DatabaseException("Database Exception!");
        }

        MessageResponse messageResponse = new MessageResponse("done");

        System.out.println("Responding with: " + JsonUtil.toJson(messageResponse));
        System.out.println(UserController.lineBreak);

        return messageResponse;
    }
}
