package seng302.api.services;

import seng302.api.controllers.UserController;
import seng302.util.DatabaseUtil;
import seng302.util.JsonUtil;
import seng302.util.exceptions.ApiKeyInvalidException;
import seng302.util.exceptions.JsonMalformedException;
import seng302.util.exceptions.JsonParseException;
import seng302.util.object.MessageResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The result service class.
 *
 * @author avh17, wrs35
 */
public class ResultService {
    /**
     * Handles a note result post request.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param body A json string containing difficulty and params.
     * @return A 201 OK header.
     */
    public MessageResponse postScheduleResults(String ip, String url, String[] routeSplat,
                                               String body) {
        System.out.println("Request by: " + ip + "\nOn route: POST " + url);
        System.out.println("Body: " + body);

        String thisApiKey = routeSplat[0];

        Boolean isValid = DatabaseUtil.validateApiKey(thisApiKey);

        if (!isValid) {
            throw new ApiKeyInvalidException("invalid");
        }

        // check json is valid
        Map<String, Object> jsonMap;

        try {
            jsonMap = JsonUtil.fromJson(body);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JsonMalformedException("Json object malformed.");
        }

        // check that everything in the json map is valid
        if (jsonMap.get("id") == null) {
            throw new JsonParseException("A schedule id is required.");
        }

        // for each answer in the result (excluding the id key)
        for (Integer i = 1; i < jsonMap.size(); i++) {
            Map<String, Object> thisResult = (Map) jsonMap.get(i.toString());

            // validate the current result
            if (thisResult.get("type") == null) {
                throw new JsonParseException("A schedule result must have a type.");
            } else if (thisResult.get("question") == null) {
                throw new JsonParseException("A schedule result must have a question.");
            } else if (thisResult.get("params") == null) {
                throw new JsonParseException("A schedule result must have params.");
            } else if (thisResult.get("rating") == null) {
                throw new JsonParseException("A schedule result must have a rating.");
            }
        }

        // since it hasn't thrown at this point, assume its correct and we'll attempt to save it
        Integer scheduleId = ((Double) jsonMap.get("id")).intValue();
        Integer ownerId = DatabaseUtil.getUserIdFromApiKey(thisApiKey);
        Long epoch = System.currentTimeMillis() / 1000;

        LinkedHashMap<String, HashMap<String, Object>> questionResults = new LinkedHashMap<>();

        // throw each json question result into a nice neat java array
        for (Integer i = 1; i < jsonMap.size(); i++) {
            Map<String, Object> thisResultMap = (Map) jsonMap.get(i.toString());

            HashMap<String, Object> thisQuestionResult = new HashMap<>();

            thisQuestionResult.put("type", thisResultMap.get("type"));
            thisQuestionResult.put("question", thisResultMap.get("question"));
            thisQuestionResult.put("hint", thisResultMap.get("hint"));
            thisQuestionResult.put("params", thisResultMap.get("params"));
            thisQuestionResult.put("rating", ((Double) thisResultMap.get("rating")).intValue());

            questionResults.put(i.toString(), thisQuestionResult);
        }

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(
                    DatabaseUtil.getFullConnectionString());

                // check if a schedule exists for this schedule id with this user id
                PreparedStatement scheduleIdExistsQuery = connection.prepareStatement(
                    "select count(*) as 'count' from schedules "
                        + "where schedule_id=? and owner_id=?;");

                scheduleIdExistsQuery.setInt(1, scheduleId);
                scheduleIdExistsQuery.setInt(2, ownerId);
                scheduleIdExistsQuery.setQueryTimeout(10);

                ResultSet scheduleIdExistsResult = scheduleIdExistsQuery.executeQuery();

                if (scheduleIdExistsResult.getInt("count") != 1) {
                    throw new JsonParseException("Invalid schedule id.");
                }

                // if we got back a count of 1, then we know that the user owns this schedule
                // id so we can go ahead and save it to the db now
                PreparedStatement insertScheduleQuery = connection.prepareStatement(
                    "insert into schedule_attempts (schedule_id, owner_id, time_created) "
                        + "values (?, ?, ?);");

                insertScheduleQuery.setInt(1, scheduleId);
                insertScheduleQuery.setInt(2, ownerId);
                insertScheduleQuery.setLong(3, epoch);

                insertScheduleQuery.setQueryTimeout(10);

                insertScheduleQuery.executeUpdate();

                // get the newest schedule's schedule_id
                PreparedStatement attemptIdQuery = connection.prepareStatement(
                    "select attempt_id from schedule_attempts where time_created=?;");

                attemptIdQuery.setLong(1, epoch);
                attemptIdQuery.setQueryTimeout(10);

                ResultSet attemptIdResult = attemptIdQuery.executeQuery();
                Integer attemptId = attemptIdResult.getInt("attempt_id");

                try {
                    // for each result, add a new row to the question attempts
                    for (Map.Entry<String, HashMap<String, Object>> entry
                        : questionResults.entrySet()) {

                        String type = (String) entry.getValue().get("type");
                        String question = (String) entry.getValue().get("question");
                        String hint = (String) entry.getValue().get("hint");
                        String params = JsonUtil.toJson(entry.getValue().get("params"));
                        Integer rating = (Integer) entry.getValue().get("rating");

                        PreparedStatement questionAttemptQuery = connection.prepareStatement(
                            "insert into schedule_attempt_answers "
                                + "(attempt_id, schedule_question_id, schedule_question_type, "
                                + "actual_question, actual_answer, params, rating) "
                                + "values (?, ?, ?, ?, ?, ?, ?);");

                        questionAttemptQuery.setInt(1, attemptId);
                        questionAttemptQuery.setInt(2, Integer.parseInt(entry.getKey()));
                        questionAttemptQuery.setString(3, type);
                        questionAttemptQuery.setString(4, question);
                        questionAttemptQuery.setString(5, hint);
                        questionAttemptQuery.setString(6, params);
                        questionAttemptQuery.setInt(7, rating);

                        questionAttemptQuery.setQueryTimeout(10);

                        questionAttemptQuery.executeUpdate();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

        MessageResponse messageResponse = new MessageResponse("done");

        System.out.println("Responding with: " + JsonUtil.toJson(messageResponse));
        System.out.println(UserController.lineBreak);

        return messageResponse;
    }
}
