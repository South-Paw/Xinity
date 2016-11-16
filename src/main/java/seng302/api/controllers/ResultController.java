package seng302.api.controllers;

import static seng302.api.controllers.UserController.lineBreak;
import static seng302.util.JsonUtil.json;
import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.post;

import seng302.api.services.ResultService;
import seng302.util.JsonUtil;
import seng302.util.exceptions.ApiKeyInvalidException;
import seng302.util.exceptions.JsonMalformedException;
import seng302.util.exceptions.JsonParseException;
import seng302.util.object.ErrorResponse;

/**
 * The result controller.
 *
 * @author avh17, wrs35
 */
public class ResultController {

    /**
     * Route constructor for the controller.
     *
     * @param resultService The tutor services we use for routing various methods.
     */
    public ResultController(final ResultService resultService) {

        // ==== API ROUTES ========================================================================

        post(
            "/api/v1/*/result/schedule",
            ((request, response) -> resultService.postScheduleResults(
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
