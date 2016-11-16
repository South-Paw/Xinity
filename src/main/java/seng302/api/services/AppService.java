package seng302.api.services;

import seng302.api.controllers.UserController;
import seng302.util.JsonUtil;
import seng302.util.object.AppResponse;

/**
 * The app services class. Holds the fancy business logic and does the heavy lifting for the
 * API routes and produces things that they can return.
 *
 * @author adg62
 */
public class AppService {

    /**
     * The GET "/api/v1/app" method. Will response with application information for the client.
     * Expects: A GET request.
     *
     * @param ip The request ip.
     * @param url The request route.
     * @return A valid json object containing the app response variables.
     */
    public AppResponse getApp(String ip, String url) {
        System.out.println("Request by: " + ip + "\nOn route: GET " +  url);

        AppResponse appResponse = new AppResponse();

        System.out.println("Responding with: " + JsonUtil.toJson(appResponse));
        System.out.println(UserController.lineBreak);

        return appResponse;
    }
}
