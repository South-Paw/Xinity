package seng302.api.controllers;

import static seng302.util.JsonUtil.json;

import static spark.Spark.after;
import static spark.Spark.get;

import seng302.api.services.AppService;

/**
 * The app controller class. Manages API routes for app info requests.
 *
 * @author adg62
 */
public class AppController {

    /**
     * Route constructor for the controller.
     *
     * @param appService The app service methods.
     */
    public AppController(final AppService appService) {

        // ==== API ROUTES ========================================================================

        get(
            "/api/v1/app",
            ((request, response) -> appService.getApp(request.ip(), request.url())),
            json()
        );

        // ==== AFTER EVERY REQUEST ===============================================================

        after(((request, response) -> {
            response.type("application/json");
        }));
    }
}
