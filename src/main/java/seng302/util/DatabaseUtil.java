package seng302.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database utility. Assists with SQLite database operations.
 */
public final class DatabaseUtil {

    private static String connectionString = "jdbc:sqlite:";
    private static String databaseName = "Xinity.db";

    private static String fullConnectionString =
        connectionString + System.getProperty("user.dir") + "/" + databaseName;

    /**
     * Start the database services, creates the tables if they don't exist and will clear and
     * populate them with test data if the mode is set to "test".
     *
     * @param mode "test" if you want to clear the databases and populate them with test data.
     */
    public static void startDatabaseServices(String mode) {
        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(fullConnectionString);

                Statement statement = connection.createStatement();
                statement.setQueryTimeout(10);

                createTables(statement);

                if (mode != null && mode.equals("test")) {
                    populateWithTestData(statement);
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

    /**
     * Creates the default tables.
     *
     * @param statement A Statement object for executing with.
     */
    private static void createTables(Statement statement) {
        try {
            Class.forName("org.sqlite.JDBC");

            try {
                // Create databases
                statement.executeUpdate("CREATE TABLE if not exists `users` ("
                    + "`id` integer primary key, "
                    + "`username` string UNIQUE, "
                    + "`password` string, "
                    + "`apikey` string)");

                statement.executeUpdate("CREATE TABLE if not exists `user_details` ("
                    + "`id` integer primary key, "
                    + "`name` string, "
                    + "`email` string, "
                    + "`location` string, "
                    + "`bio` text, "
                    + "`imgurl` string default 'unknown', "
                    + "`theme` string default 'dark', "
                    + "`tempo` integer default '120')");

                statement.executeUpdate("CREATE TABLE if not exists `schedules` ("
                    + "`schedule_id` integer primary key, "
                    + "`owner_id` integer, "
                    + "`name` string, "
                    + "`difficulty` string, "
                    + "`random` string, "
                    + "`time_created` string)");

                statement.executeUpdate("CREATE TABLE if not exists `schedule_questions` ("
                    + "`schedule_question_id` integer primary key, "
                    + "`schedule_id` integer, "
                    + "`question_id` integer, "
                    + "`type` string, "
                    + "`params` string, "
                    + "`quantity` integer)");

                statement.executeUpdate("CREATE TABLE if not exists `schedule_attempts` ("
                    + "`attempt_id` integer primary key, "
                    + "`schedule_id` integer, "
                    + "`owner_id` integer, "
                    + "`time_created` string)");

                statement.executeUpdate("CREATE TABLE if not exists `schedule_attempt_answers` ("
                    + "`id` integer primary key, "
                    + "`attempt_id` integer, "
                    + "`schedule_question_id` integer, "
                    + "`schedule_question_type` string, "
                    + "`actual_question` string, "
                    + "`actual_answer` string, "
                    + "`params` string, "
                    + "`rating` integer)");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the contents of the existing tables and creates new ones before populating them with
     * test data.
     *
     * @param statement A Statement object for executing with.
     */
    private static void populateWithTestData(Statement statement) {
        System.out.println("Generating test data...");

        try {
            Class.forName("org.sqlite.JDBC");

            try {
                // Clear any existing tables and data
                statement.executeUpdate("drop table if exists users");
                statement.executeUpdate("drop table if exists user_details");
                statement.executeUpdate("drop table if exists schedules");
                statement.executeUpdate("drop table if exists schedule_questions");
                statement.executeUpdate("drop table if exists schedule_attempts");
                statement.executeUpdate("drop table if exists schedule_attempt_answers");

                // Create clean tables
                createTables(statement);

                // Add in a test user
                String uuid = "testuuid";

                // Add the user
                statement.executeUpdate(
                    "insert into users (id, username, password, apikey) values "
                        + "('1', 'test', 'apples', '" + uuid + "')");

                // Add their details
                statement.executeUpdate(
                    "insert into user_details (id, name, email, location) values "
                        + "('1', 'Mr Test', 'hello@iliketests.co.nz', 'New Zealand')");

                // Create schedule 1
                statement.executeUpdate(
                    "insert into schedules (owner_id, name, difficulty, random, time_created) "
                        + "values ('1', 'Hard Schedule', 'hard', 'true', '1474680716')");

                // Add 15 questions to schedule 1
                statement.executeUpdate(
                    "insert into schedule_questions "
                        + "(schedule_id, question_id, type, params, quantity) "
                        + "values ('1', '1', 'interval', '{\"rangeUpper\":\"C5\","
                        + "\"rangeLower\":\"C4\"}', '1')");
                statement.executeUpdate(
                    "insert into schedule_questions "
                        + "(schedule_id, question_id, type, params, quantity) values "
                        + "('1', '2', 'chord', '{\"unison\":true,\"arpeggio\":false,"
                        + "\"rangeUpper\":\"C5\",\"rangeLower\":\"C4\"}', '1')");
                statement.executeUpdate(
                    "insert into schedule_questions "
                        + "(schedule_id, question_id, type, params, quantity) "
                        + "values ('1', '3', 'scale', '{\"octaves\":2,\"directionDown\":false,"
                        + "\"directionUp\":true,\"style\":\"straight\",\"rangeUpper\":\"C5\","
                        + "\"rangeLower\":\"C4\"}', '1')");

                statement.executeUpdate(
                    "insert into schedule_questions "
                        + "(schedule_id, question_id, type, params, quantity) "
                        + "values ('1', '4', 'note', '{\"rangeUpper\":\"C5\",\"rangeLower\":\"C4\"}"
                        + "', '1')");

                // Create schedule 2
                statement.executeUpdate(
                    "insert into schedules (owner_id, name, difficulty, random, time_created) "
                        + "values ('1', 'Easy Schedule', 'normal', 'false', '1474681518')");

                // Add 9 questions to schedule 2
                statement.executeUpdate(
                    "insert into schedule_questions "
                        + "(schedule_id, question_id, type, params, quantity) "
                        + "values ('2', '1', 'scale', '{\"octaves\":2,\"directionDown\":false,"
                        + "\"directionUp\":true,\"style\":\"straight\",\"rangeUpper\":\"C5\","
                        + "\"rangeLower\":\"C4\"}', '3')");
                statement.executeUpdate(
                    "insert into schedule_questions "
                        + "(schedule_id, question_id, type, params, quantity) "
                        + "values ('2', '2', 'chord', '{\"unison\":true,\"arpeggio\":false,"
                        + "\"rangeUpper\":\"C5\",\"rangeLower\":\"C4\"}', '6')");

                // Create an attempt for schedule 1
                statement.executeUpdate(
                    "insert into schedule_attempts (attempt_id, schedule_id, owner_id, "
                        + "time_created) "
                        + "values ('1', '1', '1', '1474681800')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('1', '1', '1', 'chord', 'things', 'other things', 'params', '2')"
                );

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('2', '1', '2', 'interval', 'things', 'other things', 'params', "
                        + "'4')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('3', '1', '3', 'scale', 'things', 'other things', 'params', '3')"
                );

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('4', '1', '4', 'note', 'things', 'other things', 'params', '1')"
                );

                // Create another attempt for schedule 1
                statement.executeUpdate(
                    "insert into schedule_attempts (attempt_id, schedule_id, owner_id, "
                        + "time_created) "
                        + "values ('2', '1', '1', '1474682500')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('5', '2', '1', 'scale', 'things', 'other things', 'params', "
                        + "'3')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('6', '2', '2', 'note', 'things', 'other things', 'params', "
                        + "'5')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('7', '2', '3', 'interval', 'things', 'other things', 'params', "
                        + "'1')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('8', '2', '4', 'chord', 'things', 'other things', 'params', "
                        + "'3')");

                // Create an attempt for schedule 2
                statement.executeUpdate(
                    "insert into schedule_attempts (attempt_id, schedule_id, owner_id, "
                        + "time_created) "
                        + "values ('3', '2', '1', '1474683000')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('9', '3', '1', 'chord', 'things', 'other things', 'params', "
                        + "'2')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('10', '3', '2', 'chord', 'things', 'other things', 'params', "
                        + "'4')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('11', '3', '3', 'chord', 'things', 'other things', 'params', "
                        + "'3')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('12', '3', '4', 'chord', 'things', 'other things', 'params', "
                        + "'1')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('13', '3', '5', 'chord', 'things', 'other things', 'params', "
                        + "'1')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('14', '3', '6', 'chord', 'things', 'other things', 'params', "
                        + "'1')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('15', '3', '7', 'scale', 'things', 'other things', 'params', "
                        + "'1')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('16', '3', '8', 'scale', 'things', 'other things', 'params', "
                        + "'1')");

                statement.executeUpdate(
                    "insert into schedule_attempt_answers (id, attempt_id, schedule_question_id, "
                        + "schedule_question_type, actual_question, actual_answer, params, rating) "
                        + "values ('17', '3', '9', 'scale', 'things', 'other things', 'params', "
                        + "'1')");

                System.out.println("Test Users UUID = " + uuid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Finished generating test data.");
    }

    /**
     * Check if an API key is valid or note.
     *
     * @param apiKey An API key to check.
     * @return True if valid, false if not.
     */
    public static Boolean validateApiKey(String apiKey) {
        Boolean isValid = false;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                PreparedStatement apiKeyQuery = connection.prepareStatement(
                    "select count(*) as 'count' from users where apikey=?");

                apiKeyQuery.setString(1, apiKey);
                apiKeyQuery.setQueryTimeout(10);

                ResultSet apiKeyLookup = apiKeyQuery.executeQuery();

                if (apiKeyLookup.getInt("count") != 0) {
                    isValid = true;
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

        return isValid;
    }

    /**
     * Use a valid API key to get a user id.
     *
     * @param apiKey An API key to check.
     * @return The users id.
     */
    public static Integer getUserIdFromApiKey(String apiKey) {
        Integer userId = null;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                PreparedStatement userIdQuery = connection.prepareStatement(
                    "select id from users where apikey=?;");

                userIdQuery.setString(1, apiKey);
                userIdQuery.setQueryTimeout(10);

                ResultSet userIdResult = userIdQuery.executeQuery();
                userId = userIdResult.getInt("id");
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

        return userId;
    }

    /**
     * Get the full connection string.
     *
     * @return The connection string for database connections.
     */
    public static String getFullConnectionString() {
        return fullConnectionString;
    }
}
