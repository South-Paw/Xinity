package seng302.api.services;

import seng302.api.controllers.UserController;
import seng302.util.DatabaseUtil;
import seng302.util.JsonUtil;
import seng302.util.PracticeScheduleUtil;
import seng302.util.enumerator.ChordQuality;
import seng302.util.enumerator.Interval;
import seng302.util.enumerator.ScaleType;
import seng302.util.exceptions.ApiKeyInvalidException;
import seng302.util.exceptions.JsonMalformedException;
import seng302.util.exceptions.JsonParseException;
import seng302.util.object.QuestionResponse;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The tutor service class.
 */
public class TutorService {

    /**
     * Handles a note tutor post request.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param body A json string containing difficulty and params.
     * @return A 201 OK header.
     */
    public QuestionResponse postTutorNote(String ip, String url, String[] routeSplat, String body) {
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

        // Parse the arguments
        String difficulty = (String) jsonMap.get("difficulty");
        XinityNote rangeOne;
        XinityNote rangeTwo;
        try {
            rangeOne = new XinityNote((String) jsonMap.get("rangeLower"));
            rangeTwo = new XinityNote((String) jsonMap.get("rangeUpper"));
        } catch (Exception e) {
            throw new JsonParseException("Incorrect range values.");
        }

        // Generate the question
        QuestionResponse question = PracticeScheduleUtil.generatePlayNoteQuestion(difficulty,
            rangeOne, rangeTwo);

        System.out.println("Responding with: " + JsonUtil.toJson(question));
        System.out.println(UserController.lineBreak);

        return question;
    }

    /**
     * Handles a note tutor post request.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param body A json string containing difficulty and params.
     * @return A 201 OK header.
     */
    public QuestionResponse postTutorInterval(
        String ip, String url, String[] routeSplat, String body) {
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

        // Parse the arguments
        String difficulty = (String) jsonMap.get("difficulty");
        XinityNote rangeOne;
        XinityNote rangeTwo;
        try {
            rangeOne = new XinityNote((String) jsonMap.get("rangeLower"));
            rangeTwo = new XinityNote((String) jsonMap.get("rangeUpper"));
        } catch (Exception e) {
            throw new JsonParseException("Incorrect range values.");
        }

        // Generate the question
        QuestionResponse question = PracticeScheduleUtil.generatePlayIntervalQuestion(
            difficulty, rangeOne, rangeTwo, Arrays.asList(Interval.values()));

        System.out.println("Responding with: " + JsonUtil.toJson(question));
        System.out.println(UserController.lineBreak);

        return question;
    }

    /**
     * Handles a note tutor post request.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param body A json string containing difficulty and params.
     * @return A 201 OK header.
     */
    public QuestionResponse postTutorChord(
        String ip, String url, String[] routeSplat, String body) {
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

        // Parse the arguments
        String difficulty = (String) jsonMap.get("difficulty");
        Boolean unison = (Boolean) jsonMap.get("unison");
        Boolean arpeggio = (Boolean) jsonMap.get("arpeggio");
        XinityNote rangeOne;
        XinityNote rangeTwo;
        try {
            rangeOne = new XinityNote((String) jsonMap.get("rangeLower"));
            rangeTwo = new XinityNote((String) jsonMap.get("rangeUpper"));
        } catch (Exception e) {
            throw new JsonParseException("Incorrect range values.");
        }

        // Generate the list of chord qualities based on difficulty
        List<ChordQuality> chordQualities = new ArrayList<>();
        if (difficulty.equalsIgnoreCase("hard")) {
            chordQualities = Arrays.asList(ChordQuality.values());
        } else {
            chordQualities.add(ChordQuality.MAJORTRIAD);
            chordQualities.add(ChordQuality.MINORTRIAD);
        }

        // Generate the question
        QuestionResponse question = PracticeScheduleUtil.generatePlayChordQuestion(
            difficulty, rangeOne, rangeTwo, chordQualities, arpeggio, unison);

        System.out.println("Responding with: " + JsonUtil.toJson(question));
        System.out.println(UserController.lineBreak);

        return question;
    }

    /**
     * Handles a note tutor post request.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param body A json string containing difficulty and params.
     * @return A 201 OK header.
     */
    public QuestionResponse postTutorScale(
        String ip, String url, String[] routeSplat, String body) {
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

        // Parse the arguments
        String difficulty = (String) jsonMap.get("difficulty");
        Integer octaves = ((Double) jsonMap.get("octaves")).intValue();
        Boolean directionDown = (Boolean) jsonMap.get("directionDown");
        Boolean directionUp = (Boolean) jsonMap.get("directionUp");
        String style = (String) jsonMap.get("style");
        XinityNote rangeOne;
        XinityNote rangeTwo;
        try {
            rangeOne = new XinityNote((String) jsonMap.get("rangeLower"));
            rangeTwo = new XinityNote((String) jsonMap.get("rangeUpper"));
        } catch (Exception e) {
            throw new JsonParseException("Incorrect range values.");
        }

        // Generate the list of scale types based on difficulty
        List<ScaleType> scaleTypes = new ArrayList<>();
        if (difficulty.equalsIgnoreCase("hard")) {
            scaleTypes = Arrays.asList(ScaleType.values());
        } else {
            scaleTypes.add(ScaleType.MAJOR);
            scaleTypes.add(ScaleType.MINOR);
        }

        // Generate the question
        QuestionResponse question = PracticeScheduleUtil.generatePlayScaleQuestion(
            difficulty, rangeOne, rangeTwo, scaleTypes, octaves, directionDown, directionUp, style);

        System.out.println("Responding with: " + JsonUtil.toJson(question));
        System.out.println(UserController.lineBreak);

        return question;
    }

}
