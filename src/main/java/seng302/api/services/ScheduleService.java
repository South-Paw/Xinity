package seng302.api.services;

import seng302.api.controllers.UserController;
import seng302.util.DatabaseUtil;
import seng302.util.JsonUtil;
import seng302.util.NoteUtil;
import seng302.util.enumerator.Interval;
import seng302.util.exceptions.ApiKeyInvalidException;
import seng302.util.exceptions.BadRouteParamsException;
import seng302.util.exceptions.DatabaseException;
import seng302.util.exceptions.JsonMalformedException;
import seng302.util.exceptions.JsonParseException;
import seng302.util.object.MessageResponse;
import seng302.util.object.ScheduleListResponse;
import seng302.util.object.XinityNote;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The schedule services class. Manages methods for getting, adding updating and deleting schedules.
 *
 * @author adg62
 */
public class ScheduleService {

    /**
     * Get all of a user's schedules from the database and return them.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param routeSplat The route splat.
     * @return A list of all the schedules for this user.
     */
    public ScheduleListResponse getAllSchedules(String ip, String url, String[] routeSplat) {
        System.out.println("Request by: " + ip + "\nOn route: GET " +  url);
        String apiKey = routeSplat[0];

        Boolean isValid = DatabaseUtil.validateApiKey(apiKey);

        if (!isValid) {
            throw new ApiKeyInvalidException("invalid");
        }

        ArrayList<Object> scheduleList = null;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                // get this api key's user id
                PreparedStatement apiKeyOwner = connection.prepareStatement(
                    "select * from users where apikey=?;");

                apiKeyOwner.setString(1, apiKey);
                apiKeyOwner.setQueryTimeout(10);

                ResultSet userIdOfApiKey = apiKeyOwner.executeQuery();

                Integer thisUsersId = userIdOfApiKey.getInt("id");

                // get all schedules that belong to this user id
                PreparedStatement usersSchedules = connection.prepareStatement(
                    "select * from schedules where owner_id=?;");

                usersSchedules.setInt(1, thisUsersId);
                usersSchedules.setQueryTimeout(10);

                ResultSet userScheduleList = usersSchedules.executeQuery();

                scheduleList = new ArrayList<>();

                // for each of the users schedules
                while (userScheduleList.next()) {
                    // get the number of questions it contains
                    PreparedStatement howManyQuestions = connection.prepareStatement(
                        "select sum(quantity) as 'count' from schedule_questions "
                            + "where schedule_id=?;");

                    howManyQuestions.setInt(1, userScheduleList.getInt("schedule_id"));
                    howManyQuestions.setQueryTimeout(10);

                    ResultSet numberOfQuestions = howManyQuestions.executeQuery();

                    // add this schedule to the object we'll return
                    LinkedHashMap<Object, Object> schedule = new LinkedHashMap<>();

                    schedule.put("id", userScheduleList.getInt("schedule_id"));
                    schedule.put("name", userScheduleList.getString("name"));
                    schedule.put("qnum", numberOfQuestions.getInt("count"));

                    scheduleList.add(schedule);
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

        ScheduleListResponse scheduleListResponse = new ScheduleListResponse(scheduleList);

        System.out.println("Responding with: " + JsonUtil.toJson(scheduleListResponse));
        System.out.println(UserController.lineBreak);

        return scheduleListResponse;
    }

    /**
     * Receives a schedule object and saves it to the database.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param routeSplat The route splat.
     * @param body The request body.
     * @return A done message.
     */
    public MessageResponse postSchedule(String ip, String url, String[] routeSplat, String body) {
        System.out.println("Request by: " + ip + "\nOn route: POST " +  url);
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

        // do json parse checking
        if (jsonMap.get("name") == null) {
            throw new JsonParseException("A schedule name is required.");
        } else if (jsonMap.get("difficulty") == null) {
            throw new JsonParseException("A schedule difficulty is required.");
        } else if (jsonMap.get("random") == null) {
            throw new JsonParseException("A schedule random boolean is required.");
        } else if (jsonMap.get("questions") == null) {
            throw new JsonParseException("A schedule question object is required.");
        }

        if (((String) jsonMap.get("name")).length() > 30) {
            throw new JsonParseException("Schedule name cannot be more than 30 characters.");
        }

        if (!(jsonMap.get("difficulty")).equals("normal")
            && !(jsonMap.get("difficulty")).equals("hard")) {
            throw new JsonParseException("Schedule difficulty can only be normal or hard.");
        }

        if (!(jsonMap.get("random")).equals(true)
            && !(jsonMap.get("random")).equals(false)) {
            throw new JsonParseException("Schedule random boolean can only be true or false.");
        }

        Map<String, Object> questionMap = (Map) jsonMap.get("questions");

        if (questionMap.size() > 10) {
            throw new JsonParseException("Sorry, you cannot have more than 10 questions in a "
                + "schedule (at the moment).");
        }

        Integer questionNumber = 1;
        LinkedHashMap<Integer, HashMap<String, Object>> dbQuestions = new LinkedHashMap<>();
        String errorString = "Question set may be malformed or invalid.";

        try {
            for (Map.Entry<String, Object> entry : questionMap.entrySet()) {
                Map<String, Object> question = (Map) entry.getValue();

                String thisQuestionType = (String) question.get("type");

                switch (thisQuestionType) {
                    case "chord":
                        Integer chordQuantity = ((Double) question.get("quantity")).intValue();
                        Boolean unison = (Boolean) question.get("unison");
                        Boolean arpeggio = (Boolean) question.get("arpeggio");
                        final XinityNote chordRangeLower = new XinityNote((String) question.get(
                            "rangeLower"));
                        final XinityNote chordRangeUpper = new XinityNote((String) question.get(
                            "rangeUpper"));

                        if (chordQuantity < 1 || chordQuantity > 20) {
                            errorString = "Invalid number of chord questions. "
                                + "Cannot have more than 20 or less than 1.";
                            throw new JsonParseException(errorString);
                        }

                        if (!unison && !arpeggio) {
                            errorString = "A chord question must be either Unison "
                                + "or Arpeggio or both.";
                            throw new JsonParseException(errorString);
                        }

                        if (!NoteUtil.isValidMidiRange(chordRangeLower.getMidi())
                            || !NoteUtil.isValidMidiRange(chordRangeUpper.getMidi())) {
                            errorString = "Invalid note question range.";
                            throw new JsonParseException(errorString);
                        }

                        HashMap<String, Object> thisChord = new HashMap<>();

                        thisChord.put("type", thisQuestionType);
                        thisChord.put("quantity", chordQuantity);
                        thisChord.put("unison", unison);
                        thisChord.put("arpeggio", arpeggio);
                        thisChord.put("rangeLower", chordRangeLower.getNote());
                        thisChord.put("rangeUpper", chordRangeUpper.getNote());

                        dbQuestions.put(questionNumber, thisChord);

                        questionNumber++;
                        break;
                    case "interval":
                        Integer intervalQuantity = ((Double) question.get("quantity")).intValue();
                        final XinityNote intervalRangeLower = new XinityNote((String) question.get(
                            "rangeLower"));
                        final XinityNote intervalRangeUpper = new XinityNote((String) question.get(
                            "rangeUpper"));

                        if (intervalQuantity < 1 || intervalQuantity > 20) {
                            errorString = "Invalid number of interval questions. "
                                + "Cannot have more than 20 or less than 1.";
                            throw new JsonParseException(errorString);
                        }

                        if (!NoteUtil.isValidMidiRange(intervalRangeLower.getMidi())
                            || !NoteUtil.isValidMidiRange(intervalRangeUpper.getMidi())) {
                            errorString = "Invalid note question range.";
                            throw new JsonParseException(errorString);
                        }

                        HashMap<String, Object> thisInterval = new HashMap<>();

                        thisInterval.put("type", thisQuestionType);
                        thisInterval.put("quantity", intervalQuantity);
                        thisInterval.put("rangeLower", intervalRangeLower.getNote());
                        thisInterval.put("rangeUpper", intervalRangeUpper.getNote());

                        dbQuestions.put(questionNumber, thisInterval);

                        questionNumber++;
                        break;
                    case "note":
                        Integer noteQuantity = ((Double) question.get("quantity")).intValue();
                        final XinityNote noteRangeLower = new XinityNote((String) question.get(
                            "rangeLower"));
                        final XinityNote noteRangeUpper = new XinityNote((String) question.get(
                            "rangeUpper"));

                        if (noteQuantity < 1 || noteQuantity > 20) {
                            errorString = "Invalid number of note questions. "
                                + "Cannot have more than 20 or less than 1.";
                            throw new JsonParseException(errorString);
                        }

                        if (!NoteUtil.isValidMidiRange(noteRangeLower.getMidi())
                            || !NoteUtil.isValidMidiRange(noteRangeUpper.getMidi())) {
                            errorString = "Invalid note question range.";
                            throw new JsonParseException(errorString);
                        }

                        HashMap<String, Object> thisNote = new HashMap<>();

                        thisNote.put("type", thisQuestionType);
                        thisNote.put("quantity", noteQuantity);
                        thisNote.put("rangeLower", noteRangeLower.getNote());
                        thisNote.put("rangeUpper", noteRangeUpper.getNote());

                        dbQuestions.put(questionNumber, thisNote);

                        questionNumber++;
                        break;
                    case "scale":
                        Integer scaleQuantity = ((Double) question.get("quantity")).intValue();
                        Boolean directionUp = (Boolean) question.get("directionUp");
                        Boolean directionDown = (Boolean) question.get("directionDown");
                        final Integer octaves = ((Double) question.get("octaves")).intValue();
                        final String style = (String) question.get("style");
                        final XinityNote scaleRangeLower = new XinityNote((String) question.get(
                            "rangeLower"));
                        final XinityNote scaleRangeUpper = new XinityNote((String) question.get(
                            "rangeUpper"));

                        if (scaleQuantity < 1 || scaleQuantity > 20) {
                            errorString = "Invalid number of scale questions. "
                                + "Cannot have more than 20 or less than 1.";
                            throw new JsonParseException(errorString);
                        }

                        if (!directionUp && !directionDown) {
                            errorString = "A scale direction must be either "
                                + "Up or Down or both.";
                            throw new JsonParseException(errorString);
                        }

                        if (octaves < 1 || octaves > 11) {
                            errorString = "Invalid number of scale octaves. "
                                + "Cannot have more than 1 or less than 11.";
                            throw new JsonParseException(errorString);
                        }

                        if (!NoteUtil.isValidMidiRange(scaleRangeLower.getMidi())
                            || !NoteUtil.isValidMidiRange(scaleRangeUpper.getMidi())) {
                            errorString = "Invalid note question range.";
                            throw new JsonParseException(errorString);
                        }

                        HashMap<String, Object> thisScale = new HashMap<>();

                        thisScale.put("type", thisQuestionType);
                        thisScale.put("quantity", scaleQuantity);
                        thisScale.put("directionUp", directionUp);
                        thisScale.put("directionDown", directionDown);
                        thisScale.put("octaves", octaves);
                        thisScale.put("style", style);
                        thisScale.put("rangeLower", scaleRangeLower.getNote());
                        thisScale.put("rangeUpper", scaleRangeUpper.getNote());

                        dbQuestions.put(questionNumber, thisScale);

                        questionNumber++;
                        break;
                    default:
                        errorString = "Invalid question type.";
                        throw new JsonParseException(errorString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new JsonParseException(errorString);
        }

        // save to database
        Integer userId = DatabaseUtil.getUserIdFromApiKey(thisApiKey);
        String scheduleName = (String) jsonMap.get("name");
        String difficulty = (String) jsonMap.get("difficulty");
        String random = (jsonMap.get("random")).toString();
        Long epoch = System.currentTimeMillis() / 1000;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                // add the new schedule into the database
                PreparedStatement insertScheduleQuery = connection.prepareStatement(
                    "insert into schedules (owner_id, name, difficulty, random, time_created) "
                        + "values (?, ?, ?, ?, ?);");

                insertScheduleQuery.setInt(1, userId);
                insertScheduleQuery.setString(2, scheduleName);
                insertScheduleQuery.setString(3, difficulty);
                insertScheduleQuery.setString(4, random);
                insertScheduleQuery.setLong(5, epoch);

                insertScheduleQuery.setQueryTimeout(10);

                insertScheduleQuery.executeUpdate();

                // get the newest schedule's schedule_id
                PreparedStatement scheduleIdQuery = connection.prepareStatement(
                    "select schedule_id from schedules where time_created=?;");

                scheduleIdQuery.setLong(1, epoch);
                scheduleIdQuery.setQueryTimeout(10);

                ResultSet scheduleIdResult = scheduleIdQuery.executeQuery();
                Integer scheduleId = scheduleIdResult.getInt("schedule_id");

                // now add in each schedule question
                for (Map.Entry<Integer, HashMap<String, Object>> entry : dbQuestions.entrySet()) {
                    final String type = (String) entry.getValue().get("type");
                    String params;
                    final Integer quantity = (Integer) entry.getValue().get("quantity");

                    entry.getValue().remove("type");
                    entry.getValue().remove("quantity");

                    params = JsonUtil.toJson(entry.getValue());

                    PreparedStatement scheduleQuestionQuery = connection.prepareStatement(
                        "insert into schedule_questions "
                            + "(schedule_id, question_id, type, params, quantity) "
                            + "values (?, ?, ?, ?, ?);");

                    scheduleQuestionQuery.setInt(1, scheduleId);
                    scheduleQuestionQuery.setInt(2, entry.getKey());
                    scheduleQuestionQuery.setString(3, type);
                    scheduleQuestionQuery.setString(4, params);
                    scheduleQuestionQuery.setInt(5, quantity);

                    scheduleQuestionQuery.executeUpdate();
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

    /**
     * Get a single schedule by id from the database.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param routeSplat The route splat.
     * @return The requested schedule from the database.
     */
    public HashMap<String, Object> getSchedule(String ip, String url, String[] routeSplat) {
        System.out.println("Request by: " + ip + "\nOn route: GET " +  url);

        final String thisApiKey = routeSplat[0];
        final Integer thisScheduleId;

        Boolean isValid = DatabaseUtil.validateApiKey(thisApiKey);

        if (!isValid) {
            throw new ApiKeyInvalidException("invalid");
        }

        try {
            thisScheduleId = Integer.parseInt(routeSplat[1]);
        } catch (NumberFormatException e) {
            throw new BadRouteParamsException("Schedule id is invalid.");
        }

        Integer userId = DatabaseUtil.getUserIdFromApiKey(thisApiKey);

        HashMap<String, Object> thisSchedule = new HashMap<>();

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                // add the new schedule into the database
                PreparedStatement findThisScheduleQuery = connection.prepareStatement(
                    "select * from schedules where schedule_id=? and owner_id=?;");

                findThisScheduleQuery.setInt(1, thisScheduleId);
                findThisScheduleQuery.setInt(2, userId);

                findThisScheduleQuery.setQueryTimeout(10);

                ResultSet thisScheduleResult = findThisScheduleQuery.executeQuery();

                if (!thisScheduleResult.isBeforeFirst() ) {
                    throw new DatabaseException("Invalid schedule id.");
                } else {
                    thisSchedule.put("id", thisScheduleResult.getInt("schedule_id"));
                    thisSchedule.put("name", thisScheduleResult.getString("name"));
                    thisSchedule.put("difficulty", thisScheduleResult.getString("difficulty"));

                    if (thisScheduleResult.getString("random").equals("true")) {
                        thisSchedule.put("random", true);
                    } else {
                        thisSchedule.put("random", false);
                    }

                    PreparedStatement scheduleQuestionSetQuery = connection.prepareStatement(
                        "select * from schedule_questions where schedule_id=?;");

                    scheduleQuestionSetQuery.setInt(1, thisScheduleId);

                    scheduleQuestionSetQuery.setQueryTimeout(10);

                    ResultSet scheduleQuestionSetResult = scheduleQuestionSetQuery.executeQuery();

                    LinkedHashMap<Integer, Object> scheduleQuestionList = new LinkedHashMap<>();

                    while (scheduleQuestionSetResult.next()) {
                        HashMap<String, Object> thisQuestion = new HashMap<>();

                        thisQuestion.put("type", scheduleQuestionSetResult.getString("type"));
                        thisQuestion.put("quantity", scheduleQuestionSetResult.getInt("quantity"));

                        Map<String, Object> paramsMap = JsonUtil.fromJson(
                            scheduleQuestionSetResult.getString("params"));

                        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                            thisQuestion.put(entry.getKey(), entry.getValue());
                        }

                        scheduleQuestionList.put(
                            scheduleQuestionSetResult.getInt("question_id"), thisQuestion);
                    }

                    thisSchedule.put("questions", scheduleQuestionList);
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

        System.out.println("Responding with: " + JsonUtil.toJson(thisSchedule));
        System.out.println(UserController.lineBreak);

        return thisSchedule;
    }

    /**
     * Updates an existing schedule in the database.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param routeSplat The route splat.
     * @param body The request body.
     * @return A message containing "done" if it was successful.
     */
    public MessageResponse putSchedule(String ip, String url, String[] routeSplat, String body) {
        System.out.println("Request by: " + ip + "\nOn route: PUT " +  url);
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

        // do json parse checking
        if (jsonMap.get("name") == null) {
            throw new JsonParseException("A schedule name is required.");
        } else if (jsonMap.get("difficulty") == null) {
            throw new JsonParseException("A schedule difficulty is required.");
        } else if (jsonMap.get("random") == null) {
            throw new JsonParseException("A schedule random boolean is required.");
        } else if (jsonMap.get("questions") == null) {
            throw new JsonParseException("A schedule question object is required.");
        } else if (jsonMap.get("id") == null) {
            throw new JsonParseException("A schedule id is required.");
        }

        if (((String) jsonMap.get("name")).length() > 30) {
            throw new JsonParseException("Schedule name cannot be more than 30 characters.");
        }

        if (!(jsonMap.get("difficulty")).equals("normal")
                && !(jsonMap.get("difficulty")).equals("hard")) {
            throw new JsonParseException("Schedule difficulty can only be normal or hard.");
        }

        if (!(jsonMap.get("random")).equals(true)
                && !(jsonMap.get("random")).equals(false)) {
            throw new JsonParseException("Schedule random boolean can only be true or false.");
        }

        Map<String, Object> questionMap = (Map) jsonMap.get("questions");

        if (questionMap.size() > 10) {
            throw new JsonParseException("Sorry, you cannot have more than 10 questions in a "
                + "schedule (at the moment).");
        }

        Integer questionNumber = 1;
        LinkedHashMap<Integer, HashMap<String, Object>> dbQuestions = new LinkedHashMap<>();
        String errorString = "Question set may be malformed or invalid.";

        try {
            for (Map.Entry<String, Object> entry : questionMap.entrySet()) {
                Map<String, Object> question = (Map) entry.getValue();

                String thisQuestionType = (String) question.get("type");

                switch (thisQuestionType) {
                    case "chord":
                        Integer chordQuantity = ((Double) question.get("quantity")).intValue();
                        Boolean unison = (Boolean) question.get("unison");
                        Boolean arpeggio = (Boolean) question.get("arpeggio");
                        final XinityNote chordRangeLower = new XinityNote((String) question.get(
                            "rangeLower"));
                        final XinityNote chordRangeUpper = new XinityNote((String) question.get(
                            "rangeUpper"));

                        if (chordQuantity < 1 || chordQuantity > 20) {
                            errorString = "Invalid number of chord questions. "
                                + "Cannot have more than 20 or less than 1.";
                            throw new JsonParseException(errorString);
                        }

                        if (!unison && !arpeggio) {
                            errorString = "A chord question must be either Unison "
                                + "or Arpeggio or both.";
                            throw new JsonParseException(errorString);
                        }

                        if (!NoteUtil.isValidMidiRange(chordRangeLower.getMidi())
                            || !NoteUtil.isValidMidiRange(chordRangeUpper.getMidi())) {
                            errorString = "Invalid note question range.";
                            throw new JsonParseException(errorString);
                        }

                        HashMap<String, Object> thisChord = new HashMap<>();

                        thisChord.put("type", thisQuestionType);
                        thisChord.put("quantity", chordQuantity);
                        thisChord.put("unison", unison);
                        thisChord.put("arpeggio", arpeggio);
                        thisChord.put("rangeLower", chordRangeLower.getNote());
                        thisChord.put("rangeUpper", chordRangeUpper.getNote());

                        dbQuestions.put(questionNumber, thisChord);

                        questionNumber++;
                        break;
                    case "interval":
                        Integer intervalQuantity = ((Double) question.get("quantity")).intValue();
                        final XinityNote intervalRangeLower = new XinityNote((String) question.get(
                            "rangeLower"));
                        final XinityNote intervalRangeUpper = new XinityNote((String) question.get(
                            "rangeUpper"));

                        if (intervalQuantity < 1 || intervalQuantity > 20) {
                            errorString = "Invalid number of interval questions. "
                                + "Cannot have more than 20 or less than 1.";
                            throw new JsonParseException(errorString);
                        }

                        if (!NoteUtil.isValidMidiRange(intervalRangeLower.getMidi())
                            || !NoteUtil.isValidMidiRange(intervalRangeUpper.getMidi())) {
                            errorString = "Invalid note question range.";
                            throw new JsonParseException(errorString);
                        }

                        HashMap<String, Object> thisInterval = new HashMap<>();

                        thisInterval.put("type", thisQuestionType);
                        thisInterval.put("quantity", intervalQuantity);
                        thisInterval.put("rangeLower", intervalRangeLower.getNote());
                        thisInterval.put("rangeUpper", intervalRangeUpper.getNote());

                        dbQuestions.put(questionNumber, thisInterval);

                        questionNumber++;
                        break;
                    case "note":
                        Integer noteQuantity = ((Double) question.get("quantity")).intValue();
                        final XinityNote noteRangeLower = new XinityNote((String) question.get(
                            "rangeLower"));
                        final XinityNote noteRangeUpper = new XinityNote((String) question.get(
                            "rangeUpper"));

                        if (noteQuantity < 1 || noteQuantity > 20) {
                            errorString = "Invalid number of note questions. "
                                + "Cannot have more than 20 or less than 1.";
                            throw new JsonParseException(errorString);
                        }

                        if (!NoteUtil.isValidMidiRange(noteRangeLower.getMidi())
                            || !NoteUtil.isValidMidiRange(noteRangeUpper.getMidi())) {
                            errorString = "Invalid note question range.";
                            throw new JsonParseException(errorString);
                        }

                        HashMap<String, Object> thisNote = new HashMap<>();

                        thisNote.put("type", thisQuestionType);
                        thisNote.put("quantity", noteQuantity);
                        thisNote.put("rangeLower", noteRangeLower.getNote());
                        thisNote.put("rangeUpper", noteRangeUpper.getNote());

                        dbQuestions.put(questionNumber, thisNote);

                        questionNumber++;
                        break;
                    case "scale":
                        Integer scaleQuantity = ((Double) question.get("quantity")).intValue();
                        Boolean directionUp = (Boolean) question.get("directionUp");
                        Boolean directionDown = (Boolean) question.get("directionDown");
                        final Integer octaves = ((Double) question.get("octaves")).intValue();
                        final String style = (String) question.get("style");
                        final XinityNote scaleRangeLower = new XinityNote((String) question.get(
                            "rangeLower"));
                        final XinityNote scaleRangeUpper = new XinityNote((String) question.get(
                            "rangeUpper"));

                        if (scaleQuantity < 1 || scaleQuantity > 20) {
                            errorString = "Invalid number of scale questions. "
                                + "Cannot have more than 20 or less than 1.";
                            throw new JsonParseException(errorString);
                        }

                        if (!directionUp && !directionDown) {
                            errorString = "A scale direction must be either "
                                + "Up or Down or both.";
                            throw new JsonParseException(errorString);
                        }

                        if (octaves < 1 || octaves > 11) {
                            errorString = "Invalid number of scale octaves. "
                                + "Cannot have more than 1 or less than 11.";
                            throw new JsonParseException(errorString);
                        }

                        if (!NoteUtil.isValidMidiRange(scaleRangeLower.getMidi())
                            || !NoteUtil.isValidMidiRange(scaleRangeUpper.getMidi())) {
                            errorString = "Invalid note question range.";
                            throw new JsonParseException(errorString);
                        }

                        HashMap<String, Object> thisScale = new HashMap<>();

                        thisScale.put("type", thisQuestionType);
                        thisScale.put("quantity", scaleQuantity);
                        thisScale.put("directionUp", directionUp);
                        thisScale.put("directionDown", directionDown);
                        thisScale.put("octaves", octaves);
                        thisScale.put("style", style);
                        thisScale.put("rangeLower", scaleRangeLower.getNote());
                        thisScale.put("rangeUpper", scaleRangeUpper.getNote());

                        dbQuestions.put(questionNumber, thisScale);

                        questionNumber++;
                        break;
                    default:
                        errorString = "Invalid question type.";
                        throw new JsonParseException(errorString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new JsonParseException(errorString);
        }

        // save to database
        Integer userId = DatabaseUtil.getUserIdFromApiKey(thisApiKey);
        String scheduleName = (String) jsonMap.get("name");
        Integer scheduleId = ((Double) jsonMap.get("id")).intValue();
        String difficulty = (String) jsonMap.get("difficulty");
        String random = (jsonMap.get("random")).toString();
        Long epoch = System.currentTimeMillis() / 1000;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                // check if a schedule exists for this id
                PreparedStatement scheduleIdExistsQuery = connection.prepareStatement(
                    "select count(*) as 'count' from schedules "
                        + "where schedule_id=? and owner_id=?;");

                scheduleIdExistsQuery.setInt(1, scheduleId);
                scheduleIdExistsQuery.setInt(2, userId);
                scheduleIdExistsQuery.setQueryTimeout(10);

                ResultSet scheduleIdExistsResult = scheduleIdExistsQuery.executeQuery();

                if (scheduleIdExistsResult.getInt("count") != 1) {
                    throw new JsonParseException("Invalid schedule id.");
                }

                // if they do, then delete them and then re-add them
                PreparedStatement scheduleDeleteQuery = connection.prepareStatement(
                    "delete from schedules where schedule_id=?");

                scheduleDeleteQuery.setInt(1, scheduleId);
                scheduleDeleteQuery.setQueryTimeout(10);
                scheduleDeleteQuery.executeUpdate();

                PreparedStatement scheduleQuestionDeleteQuery = connection.prepareStatement(
                    "delete from schedule_questions where schedule_id=?");

                scheduleQuestionDeleteQuery.setInt(1, scheduleId);
                scheduleQuestionDeleteQuery.setQueryTimeout(10);
                scheduleQuestionDeleteQuery.executeUpdate();

                // now add the 'new' schedule into the database
                PreparedStatement insertScheduleQuery = connection.prepareStatement(
                    "insert into schedules "
                        + "(schedule_id, owner_id, name, difficulty, random, time_created) "
                        + "values (?, ?, ?, ?, ?, ?);");

                insertScheduleQuery.setInt(1, scheduleId);
                insertScheduleQuery.setInt(2, userId);
                insertScheduleQuery.setString(3, scheduleName);
                insertScheduleQuery.setString(4, difficulty);
                insertScheduleQuery.setString(5, random);
                insertScheduleQuery.setLong(6, epoch);

                insertScheduleQuery.setQueryTimeout(10);

                insertScheduleQuery.executeUpdate();

                // now add in each schedule question
                for (Map.Entry<Integer, HashMap<String, Object>> entry : dbQuestions.entrySet()) {
                    final String type = (String) entry.getValue().get("type");
                    String params;
                    final Integer quantity = (Integer) entry.getValue().get("quantity");

                    entry.getValue().remove("type");
                    entry.getValue().remove("quantity");

                    params = JsonUtil.toJson(entry.getValue());

                    PreparedStatement scheduleQuestionQuery = connection.prepareStatement(
                        "insert into schedule_questions "
                            + "(schedule_id, question_id, type, params, quantity) "
                            + "values (?, ?, ?, ?, ?);");

                    scheduleQuestionQuery.setInt(1, scheduleId);
                    scheduleQuestionQuery.setInt(2, entry.getKey());
                    scheduleQuestionQuery.setString(3, type);
                    scheduleQuestionQuery.setString(4, params);
                    scheduleQuestionQuery.setInt(5, quantity);

                    scheduleQuestionQuery.executeUpdate();
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

    /**
     * Updates an existing schedule in the database.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param routeSplat The route splat.
     */
    public MessageResponse deleteSchedule(String ip, String url, String[] routeSplat) {
        System.out.println("Request by: " + ip + "\nOn route: DELETE " +  url);
        final String thisApiKey = routeSplat[0];
        final Integer thisScheduleId = Integer.parseInt(routeSplat[1]);

        Boolean isValid = DatabaseUtil.validateApiKey(thisApiKey);

        if (!isValid) {
            throw new ApiKeyInvalidException("invalid");
        }

        Integer userId = DatabaseUtil.getUserIdFromApiKey(thisApiKey);

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DatabaseUtil.getFullConnectionString());

                // check if a schedule exists for this schedule id with this user id
                PreparedStatement scheduleIdExistsQuery = connection.prepareStatement(
                    "select count(*) as 'count' from schedules "
                        + "where schedule_id=? and owner_id=?;");

                scheduleIdExistsQuery.setInt(1, thisScheduleId);
                scheduleIdExistsQuery.setInt(2, userId);
                scheduleIdExistsQuery.setQueryTimeout(10);

                ResultSet scheduleIdExistsResult = scheduleIdExistsQuery.executeQuery();

                if (scheduleIdExistsResult.getInt("count") != 1) {
                    throw new JsonParseException("Invalid schedule id.");
                }

                // if one does, delete it
                PreparedStatement scheduleDeleteQuery = connection.prepareStatement(
                    "delete from schedules where schedule_id=?");

                scheduleDeleteQuery.setInt(1, thisScheduleId);
                scheduleDeleteQuery.setQueryTimeout(10);
                scheduleDeleteQuery.executeUpdate();

                PreparedStatement scheduleQuestionDeleteQuery = connection.prepareStatement(
                    "delete from schedule_questions where schedule_id=?");

                scheduleQuestionDeleteQuery.setInt(1, thisScheduleId);
                scheduleQuestionDeleteQuery.setQueryTimeout(10);
                scheduleQuestionDeleteQuery.executeUpdate();
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
