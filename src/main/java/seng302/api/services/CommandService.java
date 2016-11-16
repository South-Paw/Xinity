package seng302.api.services;

import seng302.Environment;
import seng302.Invoker;
import seng302.api.controllers.UserController;
import seng302.util.DatabaseUtil;
import seng302.util.JsonUtil;
import seng302.util.PlayServiceUtil;
import seng302.util.exceptions.ApiKeyInvalidException;
import seng302.util.exceptions.JsonMalformedException;
import seng302.util.exceptions.JsonParseException;
import seng302.util.object.CommandResponse;

import java.util.Map;

/**
 * The command services class. Holds the fancy business logic and does the heavy lifting for the
 * API routes and produces things that they can return.
 *
 * @author adg62
 */
public class CommandService {

    /**
     * The POST "/api/v1/command" method. Will invoke a command if it's valid and return the result.
     * Expects: A POST with a valid json object for use with the dsl.
     *
     * @param ip The request ip.
     * @param url The request url.
     * @param routeSplat The route splat.
     * @param body The request body.
     * @return A json response object with the commands result.
     */
    public CommandResponse postCommand(String ip, String url, String[] routeSplat, String body) {
        System.out.println("Request by: " + ip + "\nOn route: POST " +  url);

        Boolean isApiKeyValid = DatabaseUtil.validateApiKey(routeSplat[0]);

        if (!isApiKeyValid) {
            throw new ApiKeyInvalidException("API Key is invalid.");
        }

        Map<String, Object> jsonMap;

        try {
            jsonMap = JsonUtil.fromJson(body);
        } catch (Exception e) {
            //e.printStackTrace();
            throw new JsonMalformedException("Json object malformed.");
        }

        if (jsonMap.get("command") == null) {
            throw new JsonParseException("Didn't receive a command to invoke.");
        }

        String command = (String) jsonMap.get("command");

        Environment environment = new Environment();

        new Invoker(environment).executeCommand(command);

        Map<Object, Object> playResponse = PlayServiceUtil.getPlayResponse();

        CommandResponse response = new CommandResponse(
            environment.getOutput().replaceAll("\\r|\\n", ""), playResponse);

        System.out.println("Responding with: " + JsonUtil.toJson(response));
        System.out.println(UserController.lineBreak);

        return response;
    }
}
