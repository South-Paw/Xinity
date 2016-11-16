package seng302.controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import seng302.Environment;
import seng302.Invoker;
import seng302.command.Command;
import seng302.command.NullCommand;
import seng302.controllers.keyboard.KeyboardSceneController;
import seng302.util.PlayServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Controller for the Transcript Scene.
 */
@SuppressWarnings({"unused", "unchecked"})
public class TranscriptSceneController {

    private static TranscriptSceneController transcriptSceneController;

    @FXML private SplitMenuButton runButton;
    @FXML private MenuItem runOneSelect;
    @FXML private MenuItem runAllSelect;

    @FXML private Label commandCountLabel;
    @FXML private Button restartButton;
    @FXML private Button clearButton;

    @FXML private TextArea transcriptArea;
    @FXML private TextField inputField;
    @FXML private Button executeButton;

    private static String runMode = "all";

    // Commands entered history
    private static ArrayList<String> commandArray = new ArrayList<>();
    private static Integer commandArrayIndex = 0;

    // Commands run history
    private static Map<Integer, Map<String, Boolean>> commandsRun = new LinkedHashMap<>();
    private static Integer commandsRunIndex = 0;
    private static Integer lastRunCommand = 0;

    /**
     * Constructor for class.
     * Allows the parent access to this controller.
     */
    public TranscriptSceneController() {
        transcriptSceneController = this;
    }

    /**
     * Used to get the instance of the Transcript Scene Controller.
     *
     * @return The Transcript Scene Controller.
     */
    public static TranscriptSceneController getInstance() {
        return transcriptSceneController;
    }

    /**
     * Used to retrieve the transcript area (and it's contents).
     *
     * @return The read-only transcript area.
     */
    public TextArea getTextArea() {
        return transcriptArea;
    }

    /**
     * Used to retrieve the input field.
     *
     * @return The input field for commands.
     */
    public TextField getInputField() {
        return inputField;
    }

    /**
     * Get the info label for loaded commands.
     *
     * @return The info label object.
     */
    public Label getCommandCountLabel() {
        return commandCountLabel;
    }

    /**
     * Get the commands run map.
     *
     * @return The commands run map.
     */
    public Map getCommandsRun() {
        return commandsRun;
    }

    /**
     * Reset the commands run list.
     */
    public void resetCommandsRun() {
        commandsRunIndex = 0;
        lastRunCommand = 0;
        commandsRun.clear();
    }

    /**
     * Set the index of the commands run.
     *
     * @param commandsRunIndex New index.
     */
    public void setCommandsRunIndex(Integer commandsRunIndex) {
        this.commandsRunIndex = commandsRunIndex;
    }

    /**
     * Add's a command string to the list of executed commands that are then stored for exporting
     * along with the transcript when the time comes.
     *
     * @param command The command that was run.
     * @param type True if the command is valid, false if it was an error (aka NullCommand).
     */
    public void addCommandToRunList(String command, Boolean type) {
        Map<String, Boolean> entry = new HashMap<>();
        entry.put(command, type);

        commandsRun.put(commandsRunIndex, entry);

        commandsRunIndex++;

        if (commandsRun.size() > 0) {
            runButton.setDisable(false);
        }
    }

    /**
     * Update an item in the commands run list. Used to change null type commands to true or false.
     *
     * @param integer Key index of the item we're updating.
     * @param command The command string.
     * @param type The updated type.
     */
    public void updateCommandInRunList(Integer integer, String command, Boolean type) {
        Map<String, Boolean> entry = new HashMap<>();
        entry.put(command, type);

        commandsRun.put(integer, entry);
    }

    /**
     * Initialize function for when this scene is loaded.
     */
    @SuppressWarnings("Unused")
    public void initialize() {
        // Set the inputField as focused (but only after the GUI has been built).
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                inputField.requestFocus();
            }
        });

        inputField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue && KeyboardSceneController.getInstance() != null) {
                    KeyboardSceneController.getInstance().setLastFocusedField("Transcript");
                }
            }
        });
    }

    /**
     * FXML Action.
     * When the run button is pressed, begin running commands from the commandsRun array.
     */
    @FXML
    private void runButton(ActionEvent event) {
        runButton.setDisable(true);

        if (commandsRun.size() != 0) {
            restartButton.setDisable(false);
        }

        switch (runMode) {
            default:
                break;
            case "one":
                if (lastRunCommand != commandsRun.size()) {
                    if (commandsRun.size() != 0) {
                        runOneCommand();
                    } else {
                        commandCountLabel.setText("No commands in the runnable list.");
                    }
                }
                break;
            case "all":
                if (commandsRun.size() != 0) {
                    while (lastRunCommand < commandsRun.size()) {
                        runOneCommand();
                    }
                } else {
                    commandCountLabel.setText("No commands in the runnable list.");
                }
                break;
        }

        runButton.setDisable(false);
    }

    /**
     * Reusable code to run a single command from the commands run list.
     */
    private void runOneCommand() {
        Map toRun = commandsRun.get(lastRunCommand);

        String toRunString = toRun.keySet().toString();

        String toExecute = toRunString.replaceAll(Pattern.quote("["), "")
                .replaceAll(Pattern.quote("]"), "");

        execute(toExecute);

        commandCountLabel.setText("Command " + (lastRunCommand + 1) + " of "
                + commandsRun.size());

        lastRunCommand++;
    }

    /**
     * FXML Action.
     * Used when the menu item for run one is used. Switches to one mode.
     */
    @FXML
    private void selectRunOneButton(ActionEvent event) {
        runMode = "one";
        runButton.setText("Run One");
    }

    /**
     * FXML Action.
     * Used when the menu item for run all is used. Switches to all mode.
     */
    @FXML
    private void selectRunAllButton(ActionEvent event) {
        runMode = "all";
        runButton.setText("Run All");
    }

    /**
     * FXML Action.
     * When the run button is pressed, begin running commands from the commandsRun array.
     */
    @FXML
    private void restartButton(ActionEvent event) {
        lastRunCommand = 0;
        commandCountLabel.setText("");
        PlayServiceUtil.shutdownExecutor();
        PlayServiceUtil.restartExecutor();
    }

    /**
     * FXML Action.
     * Button to clear the transcript.
     */
    @FXML
    private void clearTranscriptButton(ActionEvent event) {
        getTextArea().clear();
    }

    /**
     * Handles the execute button being pressed.
     *
     * @param event ActionEvent
     */
    @FXML
    @SuppressWarnings("unused")
    private void executeButtonPressed(ActionEvent event) {
        execute(null);
    }

    /**
     * Handles any key being pressed when the input field has focus.
     *
     * @param event KeyEvent
     */
    @FXML
    @SuppressWarnings("unused")
    private void handleKeyEvent(KeyEvent event) {
        Boolean addCommand = false;
        Boolean clearTranscript = false;

        if (event.getCode().equals(KeyCode.ENTER)) {
            execute(null);
        } else if (event.getCode().equals(KeyCode.UP)) {
            // Sets the index for the previous command
            if (commandArray.size() > 0 && commandArrayIndex > 0) {
                clearTranscript = false;
                commandArrayIndex--;
            }

            addCommand = true;
        } else if (event.getCode().equals(KeyCode.DOWN)) {
            // Sets the index for the next command
            if (commandArrayIndex < (commandArray.size() - 1)) {
                clearTranscript = false;
                commandArrayIndex++;
            } else {
                clearTranscript = true;
            }

            addCommand = true;
        }

        if (addCommand && commandArray.size() > 0
                && commandArrayIndex < commandArray.size()) {
            // Displays the command
            ArrayList commandList = commandArray;

            if (!clearTranscript) {
                String nextCommand = commandList.get(commandArrayIndex).toString();
                inputField.clear();
                inputField.appendText(nextCommand);
            } else {
                commandArrayIndex++;
                inputField.clear();
            }
        }
    }

    /**
     * Handle a command that's been submitted and execute it.
     */
    private void execute(String command) {
        Environment environment = new Environment();

        if (command == null) {
            try {
                String input = inputField.getText();

                if (input.isEmpty()) {
                    inputField.requestFocus();
                } else {
                    // Add command to the command ArrayList
                    commandArray.add(input);
                    commandArrayIndex = commandArray.size();

                    // Echo command to transcript
                    transcriptArea.appendText("> " + input + "\n");

                    // Execute the input
                    Command cmdObject = new Invoker(environment).executeCommand(input);

                    if (cmdObject instanceof NullCommand) {
                        addCommandToRunList(input, false);
                    } else {
                        addCommandToRunList(input, true);
                    }

                    // Grab the environments output text and append it to the transcript area.
                    transcriptArea.appendText(environment.getOutput());

                    // Clean up input field
                    inputField.requestFocus();
                    inputField.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Echo command to transcript
            transcriptArea.appendText("> " + command + "\n");

            // Execute the input
            Command cmdObject = new Invoker(environment).executeCommand(command);

            if (cmdObject instanceof NullCommand) {
                updateCommandInRunList(lastRunCommand, command, false);
            } else {
                updateCommandInRunList(lastRunCommand, command, true);
            }

            // Grab the environments output text and append it to the transcript area.
            transcriptArea.appendText(environment.getOutput());
        }
    }

    /**
     * Used to add text to the transcript field at the current caret position.
     *
     * @param text The text to add to the field.
     */
    public void addTextToEntryField(String text) {
        Integer caretPosition = inputField.getCaretPosition();
        String currentText = inputField.getText();
        String firstSection = currentText.substring(0, caretPosition);
        String lastSection = currentText.substring(caretPosition);
        String finalText = firstSection + text + lastSection;
        inputField.clear();
        inputField.setText(finalText);
        inputField.positionCaret(caretPosition + text.length());
    }

    /**
     * Used by the main scene to set the command index.
     *
     * @param number The index value.
     */
    public void setCommandIndex(Integer number) {
        commandArrayIndex = number;
    }

    /**
     * Used by the main scene to get the command array.
     *
     * @return The command array.
     */
    public ArrayList<String> getCommandArray() {
        return commandArray;
    }

    /**
     * Used by the main scene to reset the command array.
     */
    public void resetCommandArray() {
        commandArray = new ArrayList<>();
    }
}