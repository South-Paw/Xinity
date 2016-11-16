package seng302.api.controllers;

import static seng302.api.controllers.UserController.lineBreak;
import static seng302.util.JsonUtil.json;
import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.post;

import seng302.api.services.TutorService;
import seng302.api.services.UserService;
import seng302.util.JsonUtil;
import seng302.util.exceptions.ApiKeyInvalidException;
import seng302.util.exceptions.JsonMalformedException;
import seng302.util.exceptions.JsonParseException;
import seng302.util.object.ErrorResponse;

/**
 * The tutor controller.
 *
 * @author avh17, ljm163
 */
public class TutorController {

    /**
     * Route constructor for the controller.
     *
     * @param tutorService The tutor services we use for routing various methods.
     */
    public TutorController(final TutorService tutorService) {

        // ==== API ROUTES ========================================================================

        post(
            "/api/v1/*/tutor/note",
            ((request, response) -> tutorService.postTutorNote(
                request.ip(), request.url(), request.splat(), request.body())),
            json()
        );

        post(
            "/api/v1/*/tutor/interval",
            ((request, response) -> tutorService.postTutorInterval(
                request.ip(), request.url(), request.splat(), request.body())),
            json()
        );

        post(
            "/api/v1/*/tutor/chord",
            ((request, response) -> tutorService.postTutorChord(
                request.ip(), request.url(), request.splat(), request.body())),
            json()
        );


        post(
            "/api/v1/*/tutor/scale",
            ((request, response) -> tutorService.postTutorScale(
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
