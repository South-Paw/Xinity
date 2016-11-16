package seng302.api.controllers;

import static seng302.api.controllers.UserController.lineBreak;
import static seng302.util.JsonUtil.json;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import seng302.api.services.ScheduleService;
import seng302.util.JsonUtil;
import seng302.util.exceptions.ApiKeyInvalidException;
import seng302.util.exceptions.JsonMalformedException;
import seng302.util.exceptions.JsonParseException;
import seng302.util.object.ErrorResponse;

/**
 * The schedule controller class. Manages API routes for schedule requests and interactions.
 *
 * @author adg62
 */
public class ScheduleController {

    /**
     * Route constructor for the controller.
     *
     * @param scheduleService The schedule service methods.
     */
    public ScheduleController(final ScheduleService scheduleService) {

        // ==== API ROUTES ========================================================================

        get(
            "/api/v1/*/schedules",
            ((request, response) -> scheduleService.getAllSchedules(
                request.ip(), request.url(), request.splat())),
            json()
        );

        post(
            "/api/v1/*/schedule",
            ((request, response) -> scheduleService.postSchedule(
                request.ip(), request.url(), request.splat(), request.body())),
            json()
        );

        get(
            "/api/v1/*/schedule/*",
            ((request, response) -> scheduleService.getSchedule(
                request.ip(), request.url(), request.splat())),
            json()
        );

        put(
            "/api/v1/*/schedule",
            ((request, response) -> scheduleService.putSchedule(
                request.ip(), request.url(), request.splat(), request.body())),
            json()
        );

        delete(
            "/api/v1/*/schedule/*",
            ((request, response) -> scheduleService.deleteSchedule(
                request.ip(), request.url(), request.splat())),
            json()
        );

        // ==== BEFORE / AFTER EVERY REQUEST ======================================================

        after(((request, response) -> response.type("application/json")));

        // ==== EXCEPTION HANDLING ================================================================

        exception(
            JsonMalformedException.class,
            (error, request, response) -> {
                ErrorResponse apiResponse = new ErrorResponse(error.getMessage());

                System.out.println("JsonMalformedException, responding with: "
                    + JsonUtil.toJson(apiResponse));
                System.out.println(lineBreak);

                response.status(400);
                response.type("application/json");
                response.body(JsonUtil.toJson(apiResponse));
            }
        );

        exception(
            JsonParseException.class,
            (error, request, response) -> {
                ErrorResponse apiResponse = new ErrorResponse(error.getMessage());

                System.out.println("JsonParseException, responding with: "
                    + JsonUtil.toJson(apiResponse));
                System.out.println(lineBreak);

                response.status(400);
                response.type("application/json");
                response.body(JsonUtil.toJson(apiResponse));
            }
        );

        exception(
            ApiKeyInvalidException.class,
            (error, request, response) -> {
                ErrorResponse apiResponse = new ErrorResponse(error.getMessage());

                System.out.println("ApiKeyInvalidException, responding with: "
                    + JsonUtil.toJson(apiResponse));
                System.out.println(lineBreak);

                response.status(400);
                response.type("application/json");
                response.body(JsonUtil.toJson(apiResponse));
            }
        );
    }
}
