package seng302;

import static spark.Spark.staticFiles;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import seng302.api.controllers.AppController;
import seng302.api.controllers.CommandController;
import seng302.api.controllers.ResultController;
import seng302.api.controllers.ScheduleController;
import seng302.api.controllers.TutorController;
import seng302.api.controllers.UserController;
import seng302.api.services.AppService;
import seng302.api.services.CommandService;
import seng302.api.services.ResultService;
import seng302.api.services.ScheduleService;
import seng302.api.services.TutorService;
import seng302.api.services.UserService;
import seng302.controllers.MainSceneController;
import seng302.util.ConfigUtil;
import seng302.util.DatabaseUtil;
import seng302.util.PlayServiceUtil;
import seng302.workspace.Project;

import java.io.IOException;
import java.net.URL;

/**
 * The starting point for all the magic...
 *
 * @author avh17, adg62
 */
public class App extends Application {

    private static Boolean inServerMode = false;

    /**
     * Where the magic begins. 3, 2, 1... let's go.
     *
     * @param args Any program arguments.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        }

        if (args.length == 1 && args[0].equals("-server")) {
            inServerMode = true;

            DatabaseUtil.startDatabaseServices(null);
            loadServer();
        }

        if (args.length == 2 && args[0].equals("-server") && args[1].equals("-withtestdata")) {
            inServerMode = true;

            DatabaseUtil.startDatabaseServices("test");
            loadServer();
        }
    }

    /**
     * Loads the application in server mode.
     */
    private static void loadServer() {
        System.out.println("Launching Xinity in Server mode...");

        // Load static files location
        staticFiles.location("/public");

        // For dev work - set this to the location that the front end files are in.
        // This way you don't have to recompile the app every time you change the front end code.
        // ==============================================================================
        // NB: MAKE SURE YOU COMMENT IT OUT BEFORE COMMITTING AS IT'LL BREAK JUNIT TESTS.
        // ==============================================================================
        //staticFiles.externalLocation("C:\\PATH\\TO\\PUBLIC\\FOLDER");

        // Load API controllers and services
        new AppController(new AppService());
        new CommandController(new CommandService());
        new UserController(new UserService());
        new ScheduleController(new ScheduleService());
        new TutorController(new TutorService());
        new ResultController(new ResultService());

        System.out.println("Application is now live at: http://localhost:4567");
    }

    /**
     * Loads the application with the javafx gui.
     *
     * @param primaryStage Primary stage for the scene.
     * @throws Exception When loading an invalid fxml file.
     */
    public void start(Stage primaryStage) throws Exception {
        Integer minWidth = 800;
        Integer minHeight = 600;

        String appName = ConfigUtil.retrieveAppName();

        try {
            // Scene Construction
            URL url = getClass().getResource("/scenes/MainScene.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            AnchorPane pane = fxmlLoader.load();
            Scene scene = new Scene(pane, minWidth, minHeight);

            // Setting Stage
            primaryStage.setScene(scene);
            primaryStage.setTitle(appName);
            primaryStage.setMinWidth(minWidth);
            primaryStage.setMinHeight(minHeight);
            primaryStage.setWidth(minWidth);
            primaryStage.setHeight(minHeight);

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    MainSceneController.getInstance().quitApp(null);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creates the first instance of the project class
        Project project = Project.getInstance();

        // Set the title with the fresh project object
        primaryStage.setTitle(appName + " - " + project.getProjectName());

        // Set the stage so MainSceneController can access it
        MainSceneController.setPrimaryStage(primaryStage);

        //Plays silence to stop the first note played hanging
        PlayServiceUtil.playSilent(1000);

        // Shows the primary stage
        primaryStage.show();
    }

    /**
     * Get if we're in server or GUI mode.
     *
     * @return True if in server mode, false if not.
     */
    public static Boolean getInServerMode() {
        return inServerMode;
    }
}
