package seng302.api.controllers;

import static seng302.util.JsonUtil.json;

import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;

import seng302.api.services.UserService;
import seng302.util.JsonUtil;
import seng302.util.exceptions.ApiKeyInvalidException;
import seng302.util.exceptions.BadLoginException;
import seng302.util.exceptions.BadRegistrationException;
import seng302.util.exceptions.DatabaseException;
import seng302.util.exceptions.JsonMalformedException;
import seng302.util.exceptions.JsonParseException;
import seng302.util.object.ErrorResponse;

/**
 * The user controller class. Manages API routes for user interactions.
 *
 * @author adg62
 */
public class UserController {

    public static String lineBreak = "------------------";

    /**
     * Route constructor for the controller.
     *
     * @param userService The user service methods.
     */
    public UserController(final UserService userService) {

        // ==== API ROUTES ========================================================================

        post(
            "/api/v1/login",
            ((request, response) -> userService.postLogin(
                request.ip(), request.url(), request.body())),
            json()
        );

        post(
            "/api/v1/register",
            ((request, response) -> userService.postRegistration(
                request.ip(), request.url(), request.body())),
            json()
        );

        post(
            "/api/v1/*/resetApiKey",
            ((request, response) -> userService.postResetApiKey(
                request.ip(), request.url(), request.splat())),
            json()
        );

        get(
            "/api/v1/*/validate",
            ((request, response) -> userService.getValidation(
                request.ip(), request.url(), request.splat())),
            json()
        );

        get(
            "/api/v1/*/user",
            ((request, response) -> userService.getProfile(
                request.ip(), request.url(), request.splat())),
            json()
        );

        post(
            "/api/v1/*/user",
            ((request, response) -> userService.postProfile(
                request.ip(), request.url(), request.splat(), request.body())),
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
            BadLoginException.class,
            (error, request, response) -> {
                ErrorResponse apiResponse = new ErrorResponse(error.getMessage());

                System.out.println("BadLoginException, responding with: "
                    + JsonUtil.toJson(apiResponse));
                System.out.println(lineBreak);

                response.status(400);
                response.type("application/json");
                response.body(JsonUtil.toJson(apiResponse));
            }
        );

        exception(
            BadRegistrationException.class,
            (error, request, response) -> {
                ErrorResponse apiResponse = new ErrorResponse(error.getMessage());

                System.out.println("BadRegistrationException, responding with: "
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

        exception(
            DatabaseException.class,
            (error, request, response) -> {
                ErrorResponse apiResponse = new ErrorResponse(error.getMessage());

                System.out.println("DatabaseException, responding with: "
                    + JsonUtil.toJson(apiResponse));
                System.out.println(lineBreak);

                response.status(500);
                response.type("application/json");
                response.body(JsonUtil.toJson(apiResponse));
            }
        );
    }
}
