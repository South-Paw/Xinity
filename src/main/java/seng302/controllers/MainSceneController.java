package seng302.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.apache.commons.lang3.StringUtils;

import seng302.controllers.about.AboutSceneController;
import seng302.controllers.dslreference.DslReferenceSceneController;
import seng302.controllers.keyboard.KeyboardSceneController;
import seng302.tutor.Tutor;
import seng302.util.ConfigUtil;
import seng302.util.PlayServiceUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.map.CommandManualMap;
import seng302.util.object.CommandManual;
import seng302.workspace.Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Controller for the Main Scene.
 * Mainly manages the menu bar and it's on click actions.
 *
 * @author avh17, adg62
 */
@SuppressWarnings({"unused", "unchecked"})
public class MainSceneController {
    /** This scene. */
    private static MainSceneController mainSceneController;

    /** Application stages/windows. */
    private static Stage primaryStage;
    private static Stage dslReferenceStage;
    private static Stage aboutStage;
    private static Stage keyboardStage;

    /** Management of currently open stages/windows. */
    private static Boolean dslReferenceStageIsOpen = false;
    private static Boolean aboutStageIsOpen = false;

    /** File paths used when saving. */
    private String transcriptFilePath = System.getProperty("user.home");
    private String projectFilePath = System.getProperty("user.home");

    /** Create a new project for the current workspace. */
    private Project project = Project.getInstance();

    private Boolean userCancelledAction = false;

    private static String appName = ConfigUtil.retrieveAppName();

    private List<Tab> tabList;

    @FXML private TabPane mainTabPane;
    @FXML private Tab transcriptTab;
    @FXML private Tab pitchTutorTab;
    @FXML private Tab intervalTutorTab;
    @FXML private Tab termTutorTab;
    @FXML private Tab scaleTutorTab;
    @FXML private Tab chordTutorTab;
    @FXML private Tab signatureTutorTab;
    @FXML private Tab spellingTutorTab;

    @FXML private Menu menuCommands;

    /**
     * Constructor for the class.
     * Allows the parent access to this controller.
     */
    public MainSceneController() {
        mainSceneController = this;
    }

    /**
     * Used to get the instance of the Main Scene Controller.
     *
     * @return The Main Scene Controller.
     */
    public static MainSceneController getInstance() {
        return mainSceneController;
    }

    /**
     * Set's the primary stage so it's in scope of this controller.
     *
     * @param primaryStageToSet The primary stage.
     */
    public static void setPrimaryStage(Stage primaryStageToSet) {
        primaryStage = primaryStageToSet;
    }

    public static Boolean getDslReferenceStageIsOpen() {
        return dslReferenceStageIsOpen;
    }

    public static void setDslReferenceStageIsOpen(Boolean dslReferenceStageIsOpen) {
        MainSceneController.dslReferenceStageIsOpen = dslReferenceStageIsOpen;
    }

    public static Boolean getAboutStageIsOpen() {
        return aboutStageIsOpen;
    }

    public static void setAboutStageIsOpen(Boolean aboutStageIsOpen) {
        MainSceneController.aboutStageIsOpen = aboutStageIsOpen;
    }

    /**
     * Used when this scene is loaded (as this stuff can't happen until the FXML exists hence why
     * it's a runLater).
     */
    @SuppressWarnings("Unused")
    public void initialize() {
        Platform.runLater(new Runnable() {
            /**
             * What this run function does in a nutshell (because it's a bit of a complicated way
             * to do it but it works); It gets the command manual map, creates a new map of
             * command categories to an arrayList.
             */
            @Override
            public void run() {
                for (Map.Entry<CommandCategory, List<CommandManual>> entry :
                        CommandManualMap.getCategoryToManualsMap().entrySet()) {

                    // Get this categories name.
                    String thisCategoriesName = entry.getKey().toString().toLowerCase();

                    // Create a menu item for it.
                    Menu thisCategory = new Menu(StringUtils.capitalize(thisCategoriesName));

                    // For each of this categories items.
                    for (CommandManual manual : entry.getValue()) {
                        // Create a menu item for this command.
                        MenuItem thisCommandItem = new MenuItem(manual.getName());

                        // Create the on action for adding the command to the transcript scene.
                        thisCommandItem.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                MainSceneController.getInstance().switchTabToTranscript();

                                TextField inputField =
                                        TranscriptSceneController.getInstance().getInputField();

                                inputField.clear();
                                inputField.requestFocus();

                                inputField.setText(manual.getQuickAdd());
                                inputField.positionCaret(manual.getQuickAdd().length() - 1);
                            }
                        });

                        // Add the command item to it's categories menu.
                        thisCategory.getItems().add(thisCommandItem);
                    }

                    // Once we've done all the item's, add the category to the main scene.
                    menuCommands.getItems().add(thisCategory);
                }
            }
        });
    }

    /**
     * FXML Action.
     * Handles the New Project button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    private void newProject(ActionEvent event) {
        userCancelledAction = false;

        if (project.isDirty()) {
            unsavedChangesAlert();
        }

        // Reset the project if user didn't cancel anything past the saveProject/discard changes
        // alert.
        if (!userCancelledAction) {
            project.createNewProject();

            primaryStage.setTitle(appName + " - " + project.getProjectName());

            TranscriptSceneController.getInstance().getTextArea().clear();
        }
    }

    /**
     * FXML Action.
     * Handles the Open Project button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    private void openProject(ActionEvent event) {
        userCancelledAction = false;

        // Checks for unsaved changes
        if (project.isDirty()) {
            unsavedChangesAlert();
        }

        // Opens new project if nothing has been cancelled
        if (!userCancelledAction) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Project Folder");

            // Updates file path if a project has been saved before
            if (!Project.getInstance().isNew()) {
                projectFilePath = Project.getInstance().getProjectPath();
            }

            // Try open file chooser and select a project
            try {
                File directory = new File(projectFilePath);
                directoryChooser.setInitialDirectory(directory);

                File openedFile = directoryChooser.showDialog(primaryStage);

                if (openedFile != null) {
                    project.createNewProject();
                    project.openProject(openedFile);

                    primaryStage.setTitle(appName + " - " + project.getProjectName());

                    TranscriptSceneController.getInstance().getTextArea().clear();
                }
            } catch (Exception e) {
                // Throw error dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");
                alert.setHeaderText("Could not open File");
                alert.setContentText("Could not open file at location:\n" + transcriptFilePath);

                alert.showAndWait();
            }
        }
    }

    /**
     * FXML Action.
     * Handles the Save Project button being pressed. If the project is marked as a new project,
     * then we need a location to saveProject it to (so use the same method as the Save Project
     * As button). Otherwise, if we have a location for this project already - saveProject it there.
     * Note: If for some reason the project doesn't have a location to save to, then use the
     *       saveAsAction.
     *
     * @param event The button event.
     */
    @FXML
    private void saveProject(ActionEvent event) {
        if (project.isNew()) {
            saveAsAction();
        } else {
            try {
                project.saveProject();

                primaryStage.setTitle(appName + " - " + project.getProjectName());
            } catch (Exception e) {
                saveAsAction();
            }
        }
    }

    /**
     * FXML Action.
     * Handles the Save Project As button being pressed.
     *
     * @param event Save button event.
     */
    @FXML
    private void saveProjectAs(ActionEvent event) {
        saveAsAction();
    }

    /**
     * FXML Action.
     * Handle the Open Transcript button being pressed. Manages opening and loading in a file to
     * the transcript area.
     *
     * @param event Open button event.
     * @throws Exception Unable to openProject file.
     */
    @FXML
    private void openTranscript(ActionEvent event) throws Exception {
        // Creates file chooser.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Transcript File");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "TXT files (*.txt)", "*.txt");

        fileChooser.getExtensionFilters().add(extFilter);

        // Opens to last used directory.
        File directory = new File(transcriptFilePath);
        fileChooser.setInitialDirectory(directory);
        File openedFile = fileChooser.showOpenDialog(primaryStage);

        try {
            // Sets last used directory.
            String fileName = openedFile.getName();
            transcriptFilePath = openedFile.getPath();

            Integer pathNameToBeStripped = (transcriptFilePath.length() - fileName.length() - 1);

            transcriptFilePath = transcriptFilePath.substring(0, pathNameToBeStripped);

            try {
                TranscriptSceneController.getInstance().getTextArea().clear();
                TranscriptSceneController.getInstance().resetCommandsRun();

                // Read the selected file.
                String fileString = readTranscriptFile(openedFile.getPath());

                // Add it to the transcript window.
                TranscriptSceneController.getInstance().getTextArea().appendText(fileString);

                // Set the command array index
                ArrayList commandArray = TranscriptSceneController.getInstance().getCommandArray();
                TranscriptSceneController.getInstance().setCommandIndex(commandArray.size());

                // Set the commands run index
                Map commandsRun = TranscriptSceneController.getInstance().getCommandsRun();
                TranscriptSceneController.getInstance().setCommandsRunIndex(commandsRun.size());

                if (commandsRun.size() == 0) {
                    TranscriptSceneController.getInstance().getCommandCountLabel().setText(
                            "No Commands Loaded");
                } else {
                    TranscriptSceneController.getInstance().getCommandCountLabel().setText(
                            commandsRun.size() + " Commands Loaded");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FXML Action.
     * Handles the Save Full Transcript button being pressed. Writes the transcript to a location
     * as a .txt file which includes all the headers and content.
     *
     * @param event Button event.
     */
    @FXML
    private void saveFullTranscript(ActionEvent event) {
        Boolean allowErrors = false;

        if (commandsRunHasErrors()) {
            allowErrors = transcriptErrorsAlert();
        }

        saveTranscriptAction("Transcript", allowErrors);
    }

    /**
     * FXML Action.
     * Handles the Save Transcript only Commands button being pressed. Writes the transcript to a
     * location as a .txt file which includes only the runnable commands.
     *
     * @param event Button event.
     */
    @FXML
    private void saveTranscriptCommands(ActionEvent event) {
        Boolean allowErrors = false;

        if (commandsRunHasErrors()) {
            allowErrors = transcriptErrorsAlert();
        }

        saveTranscriptAction("Commands", allowErrors);
    }

    /**
     * FXML Action.
     * Open the DSL Reference card.
     */
    @FXML
    private void openDslReference() {
        if (!dslReferenceStageIsOpen) {
            dslReferenceStage = new Stage();
            Parent root;

            try {
                root = FXMLLoader.load(getClass().getResource(
                        "/scenes/dslreference/DslReferenceScene.fxml"));
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            dslReferenceStage.setScene(new Scene(root));
            dslReferenceStage.setTitle("DSL Reference");
            dslReferenceStage.setResizable(false);
            dslReferenceStage.setMinHeight(600);
            dslReferenceStage.setMinWidth(800);
            dslReferenceStage.show();

            dslReferenceStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    dslReferenceStageIsOpen = false;
                }
            });

            // Load the references in category mode (0)
            DslReferenceSceneController.getInstance().loadDslReference(0);

            dslReferenceStageIsOpen = true;
        } else {
            dslReferenceStage.toFront();
        }
    }

    /**
     * FXML Action.
     * Open the About window.
     */
    @FXML
    private void openAbout() {
        if (!aboutStageIsOpen) {
            aboutStage = new Stage();
            Parent root;

            try {
                root = FXMLLoader.load(getClass().getResource(
                        "/scenes/about/AboutScene.fxml"));
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            aboutStage.setScene(new Scene(root));
            aboutStage.setTitle("About Xinity");
            aboutStage.setResizable(false);
            aboutStage.show();

            aboutStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    aboutStageIsOpen = false;
                }
            });

            AboutSceneController.getInstance().loadAboutScene();

            aboutStageIsOpen = true;
        } else {
            aboutStage.toFront();
        }
    }

    /**
     * FXML Action.
     * Quit the application.
     *
     * @param event Quit button event.
     */
    @FXML
    public void quitApp(ActionEvent event) {
        if (project.isDirty()) {
            unsavedChangesAlert();
        }

        PlayServiceUtil.shutdownExecutor();

        if (keyboardStage != null) {
            keyboardStage.close();
        }

        if (dslReferenceStageIsOpen) {
            dslReferenceStage.close();
        }

        if (aboutStageIsOpen) {
            aboutStage.close();
        }

        Platform.exit();
    }

    /**
     * Used to open the keyboard GUI.
     *
     * @param event The action event.
     */
    @FXML
    public void openKeyboard(ActionEvent event) {
        // Only opens one keyboard at a time
        if (keyboardStage == null) {
            keyboardStage = new Stage();
            Parent root;

            try {
                root = FXMLLoader.load(getClass().getResource(
                        "/scenes/keyboard/KeyboardScene.fxml"));
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            keyboardStage.setScene(new Scene(root));
            keyboardStage.setTitle("Keyboard");
            keyboardStage.setResizable(true);
            keyboardStage.show();
            keyboardStage.setResizable(false);
            keyboardStage.setWidth(1056.0);

            // Handles the keyboard being closed
            keyboardStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    keyboardStage.close();
                    keyboardStage = null;
                }
            });

            KeyboardSceneController.getInstance().loadKeyboard(keyboardStage.getWidth());
        }
    }

    /**
     * Reusable alert for managing when the user has any unsaved changes.
     */
    private void unsavedChangesAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Unsaved Changes");
        alert.setHeaderText("You have unsaved changes. Would you like to save them?");
        alert.setContentText("If you discard your changes, they will be permanently lost.");

        ButtonType cancel = new ButtonType("Cancel");
        ButtonType discard = new ButtonType("Discard");
        ButtonType save = new ButtonType("Save");

        alert.getButtonTypes().setAll(cancel, discard, save);

        alert.showAndWait();

        switch (alert.getResult().getText()) {
            case "Discard":
                break;
            case "Cancel":
                userCancelledAction = true;
                break;
            case "Save":
                saveProject(null); // This may not be a good idea but it removes duplicate code...
                break;
            default:
                break;
        }
    }

    /**
     * Check if the command array has any commands that were invalid.
     *
     * @return True if a command in the list has errors. False if none have errors.
     */
    private Boolean commandsRunHasErrors() {
        Boolean hasErrors = false;

        Map<Integer, Map<String, Boolean>> commandsRun =
                TranscriptSceneController.getInstance().getCommandsRun();

        for (Integer i = 0; i < commandsRun.size(); i++) {
            Map item = commandsRun.get(i);

            String itemString = item.keySet().toString();

            itemString = itemString.replaceAll(Pattern.quote("["), "")
                    .replaceAll(Pattern.quote("]"), "");

            if (item.get(itemString).toString().equals("false")
                    || item.get(itemString).toString().equals("null")) {
                hasErrors = true;
                break;
            }
        }

        return hasErrors;
    }

    /**
     * Ask the user if they wish to prune commands that produced errors.
     *
     * @return True if they wish to prune errors, False if they do not.
     */
    private Boolean transcriptErrorsAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Prune Commands");
        alert.setHeaderText("Do you wish to prune any invalid commands from your save file?");

        ButtonType pruneErrors = new ButtonType("Yes");
        ButtonType allowErrors = new ButtonType("No");

        alert.getButtonTypes().setAll(pruneErrors, allowErrors);

        alert.showAndWait();

        switch (alert.getResult().getText()) {
            default:
                return false;
            case "No":
                return false;
            case "Yes":
                return true;
        }
    }

    /**
     * Saves the transcript depending on the flag it receives.
     *
     * @param saveType Full Transcript or Commands only.
     * @param pruneErrors rue if they wish to prune errors, False if they do not.
     */
    private void saveTranscriptAction(String saveType, Boolean pruneErrors) {
        // Creates file chooser object and a new default file path (using users home dir).
        FileChooser fileChooser = new FileChooser();
        File directory = new File(transcriptFilePath);

        fileChooser.setTitle("Save Transcript");
        fileChooser.setInitialFileName("transcript.txt");
        fileChooser.setInitialDirectory(directory);

        // Throw the saveProject dialog
        File file = fileChooser.showSaveDialog(primaryStage);

        // Sets current directory to last used directory
        String fileName = file.getName();

        transcriptFilePath = file.getPath();
        transcriptFilePath = transcriptFilePath.substring(0,
                (transcriptFilePath.length() - fileName.length() - 1));

        // Attempt to write file
        try {
            FileWriter fileWriter = new FileWriter(file);

            // If we're doing a full save, include the transcript and the command history.
            if (saveType.equals("Transcript")) {
                fileWriter.write("#%transcript%#\n");

                fileWriter.write(TranscriptSceneController.getInstance().getTextArea().getText());

                fileWriter.write("#%history%#\n");

                ArrayList commandList = TranscriptSceneController.getInstance().getCommandArray();

                for (Object item : commandList) {
                    fileWriter.write(item.toString() + "\n");
                }
            }

            fileWriter.write("#%runnable%#\n");

            Map<Integer, Map<String, Boolean>> commandsRun =
                    TranscriptSceneController.getInstance().getCommandsRun();

            for (Integer i = 0; i < commandsRun.size(); i++) {
                Map item = commandsRun.get(i);

                String itemString = item.keySet().toString();

                itemString = itemString.replaceAll(Pattern.quote("["), "")
                          .replaceAll(Pattern.quote("]"), "");

                // If we're pruning error commands from the transcript, then do the check for them
                if (pruneErrors) {
                    if (item.get(itemString).toString().equals("true")) {
                        fileWriter.write(itemString + "\n");
                    }
                } else {
                    fileWriter.write(itemString + "\n");
                }
            }

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the project saving dialog and file chooser.
     */
    private void saveAsAction() {
        // Creates file chooser object and a new default file path (using users home dir).
        FileChooser fileChooser = new FileChooser();

        if (!Project.getInstance().isNew()) {
            projectFilePath = Project.getInstance().getProjectPath();
        }

        File directory = new File(projectFilePath);

        fileChooser.setTitle("Save Project");
        fileChooser.setInitialFileName(project.getProjectName());
        fileChooser.setInitialDirectory(directory);

        // Throw the saveProject dialog and assign file attributes
        File file;
        String dirPath;

        try {
            file = fileChooser.showSaveDialog(primaryStage);
            dirPath = file.getPath();
        } catch (Exception ex) {
            userCancelledAction = true;
            return;
        }

        Boolean makeProjectFolder = new File(dirPath).mkdir();
        Boolean makeTranscriptFolder = new File(dirPath + "/transcripts").mkdir();
        Boolean makeTermsFolder = new File(dirPath + "/terms").mkdir();

        if (!makeProjectFolder || !makeTranscriptFolder || !makeTermsFolder) {
            System.out.println("Directory could not be created!");
        } else {
            project.saveProjectAs(file);
        }

        primaryStage.setTitle(appName + " - " + project.getProjectName());
    }

    /**
     * Function for reading a given file to a string.
     *
     * @param fileName File to be opened.
     * @return The files contents as a string.
     * @throws IOException Unable to read file.
     */
    private String readTranscriptFile(String fileName) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line = bufferedReader.readLine();

            String mode = "";
            Boolean skip = false;

            while (line != null) {
                switch (line) {
                    default:
                        break;
                    case "#%transcript%#":
                        mode = "transcript";
                        skip = true;
                        break;
                    case "#%history%#":
                        mode = "history";
                        skip = true;
                        break;
                    case "#%runnable%#":
                        mode = "runnable";
                        skip = true;
                        break;
                }

                if (!skip) {
                    switch (mode) {
                        default:
                            break;
                        case "transcript":
                            stringBuilder.append(line + "\n");
                            break;
                        case "history":
                            TranscriptSceneController.getInstance().getCommandArray().add(line);
                            break;
                        case "runnable":
                            TranscriptSceneController.getInstance().addCommandToRunList(line, null);
                            break;
                    }
                }

                skip = false;
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        bufferedReader.close();

        return stringBuilder.toString();
    }

    /**
     * Used to switch the tab to the pitch tutor.
     */
    public void switchTabToTranscript() {
        mainTabPane.getSelectionModel().select(transcriptTab);
    }

    /**
     * Used to switch the tab to the pitch tutor.
     */
    public void switchTabToPitchTutor() {
        mainTabPane.getSelectionModel().select(pitchTutorTab);
    }

    /**
     * Used to switch the tab to the interval tutor.
     */
    public void switchTabToIntervalTutor() {
        mainTabPane.getSelectionModel().select(intervalTutorTab);
    }

    /**
     * Used to switch the tab to the term tutor.
     */
    public void switchTabToTermTutor() {
        mainTabPane.getSelectionModel().select(termTutorTab);
    }

    /**
     * Used to switch the tab to the scale tutor.
     */
    public void switchTabToScaleTutor() {
        mainTabPane.getSelectionModel().select(scaleTutorTab);
    }

    /**
     * Used to switch the tab to the chord tutor.
     */
    public void switchTabToTutor(Tutor.TutorType tutorType) {
        // Order must match enum Tutor.TutorType
        // NO REALLY; IT HAS TO.
        tabList = Arrays.asList(pitchTutorTab, intervalTutorTab, termTutorTab, scaleTutorTab,
                chordTutorTab, signatureTutorTab, spellingTutorTab);

        Tab desiredTab = tabList.get(tutorType.ordinal());

        mainTabPane.getSelectionModel().select(desiredTab);
    }

    /**
     * Updates the window title to show the project is now dirty.
     */
    public static void setProjectWindowTitleAsDirty() {
        if (primaryStage != null) {
            primaryStage.setTitle(appName + " - " + Project.getInstance().getProjectName() + "*");
        }
    }
}