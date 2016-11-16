package seng302.api.controllers;

import static seng302.util.JsonUtil.json;

import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.post;

import seng302.api.services.CommandService;
import seng302.util.JsonUtil;
import seng302.util.exceptions.ApiKeyInvalidException;
import seng302.util.exceptions.JsonMalformedException;
import seng302.util.exceptions.JsonParseException;
import seng302.util.exceptions.MethodErrorException;
import seng302.util.exceptions.MethodInvalidException;
import seng302.util.object.ErrorResponse;

/**
 * The command controller class. Manages API routes for commands.
 *
 * @author adg62
 */
public class CommandController {

    /**
     * Route constructor for the controller.
     *
     * @param commandService The command service methods.
     */
    public CommandController(final CommandService commandService) {

        // ==== API ROUTES ========================================================================

        post(
            "/api/v1/*/command",
            ((request, response) -> commandService.postCommand(
                request.ip(), request.url(), request.splat(), request.body())),
            json()
        );

        // ==== AFTER EVERY REQUEST ===============================================================

        after(((request, response) -> {
            response.type("application/json");
        }));

        // ==== EXCEPTION HANDLING ================================================================

        exception(
            ApiKeyInvalidException.class,
            (error, request, response) -> {
                ErrorResponse apiResponse = new ErrorResponse(error.getMessage());

                System.out.println("ApiKeyInvalidException, responding with: "
                    + JsonUtil.toJson(apiResponse));
                System.out.println(UserController.lineBreak);

                response.status(401);
                response.type("application/json");
                response.body(JsonUtil.toJson(apiResponse));
            }
        );

        exception(
            JsonMalformedException.class,
            (error, request, response) -> {
                ErrorResponse apiResponse = new ErrorResponse(error.getMessage());

                System.out.println("JsonMalformedException, responding with: "
                    + JsonUtil.toJson(apiResponse));
                System.out.println(UserController.lineBreak);

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
                System.out.println(UserController.lineBreak);

                response.status(400);
                response.type("application/json");
                response.body(JsonUtil.toJson(apiResponse));
            }
        );

        // Unused at the moment.
        exception(
            MethodInvalidException.class,
            (error, request, response) -> {
                ErrorResponse apiResponse = new ErrorResponse(error.getMessage());

                System.out.println("MethodInvalidException, responding with: "
                    + JsonUtil.toJson(apiResponse));
                System.out.println(UserController.lineBreak);

                response.status(400);
                response.type("application/json");
                response.body(JsonUtil.toJson(apiResponse));
            }
        );

        // Unused at the moment.
        exception(
            MethodErrorException.class,
            (error, request, response) -> {
                ErrorResponse apiResponse = new ErrorResponse(error.getMessage());

                System.out.println("MethodErrorException, responding with: "
                    + JsonUtil.toJson(apiResponse));
                System.out.println(UserController.lineBreak);

                response.status(400);
                response.type("application/json");
                response.body(JsonUtil.toJson(apiResponse));
            }
        );
    }
}
