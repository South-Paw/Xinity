package seng302.controllers.dslreference;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import seng302.controllers.TranscriptSceneController;
import seng302.util.object.CommandManual;

/**
 * Controller class for a dsl command item.
 *
 * @author adg62
 */
public class DslManualItemController {

    private static DslManualItemController dslCommandItemController;

    @FXML AnchorPane root;
    @FXML VBox manualVBox;
    @FXML Text name;
    @FXML Text synopsis;

    /**
     * Constructor for the class.
     */
    public DslManualItemController() {
        dslCommandItemController = this;
    }

    /**
     * Used to get the class instance.
     *
     * @return The class instance.
     */
    public static DslManualItemController getInstance() {
        return dslCommandItemController;
    }

    /**
     * Set up the scene, add the text from the manual into elements and create more as needed.
     * This was 4 hours work - if you break it I think I'll kill you. :)
     *
     * @param paneWidth The pane width we're placing this item into.
     * @param commandManual The command manual item for this view.
     */
    public void setUp(Double paneWidth, CommandManual commandManual) {
        paneWidth -= 12;

        // Add text to the name element
        name.setText(commandManual.getName());
        name.setWrappingWidth(paneWidth);

        // Add text to the synopsis element
        synopsis.setText(commandManual.getSynopsis());
        synopsis.setWrappingWidth(paneWidth);

        // Default font style
        Font titleFont = Font.font("System", FontWeight.BOLD, 12);

        // Create the description title
        Text descriptionTitle = new Text("Description:");
        descriptionTitle.setFont(titleFont);
        manualVBox.getChildren().add(descriptionTitle);

        // Default insets
        Insets separatorInsets = new Insets(8, 0, 8, 0);

        // Create the description element
        Text description = new Text(commandManual.getDescription());
        description.setWrappingWidth(paneWidth);
        manualVBox.getChildren().add(description);

        // Create a separator
        Separator descriptionSeparator = new Separator();
        descriptionSeparator.setPadding(separatorInsets);
        manualVBox.getChildren().add(descriptionSeparator);

        if (commandManual.getArguments().length() != 0) {
            // Create the arguments title
            Text argumentsTitle = new Text("Arguments:");
            argumentsTitle.setFont(titleFont);
            manualVBox.getChildren().add(argumentsTitle);

            // Create the arguments element
            Text arguments = new Text(commandManual.getArguments());
            arguments.setWrappingWidth(paneWidth);
            manualVBox.getChildren().add(arguments);

            // Create a separator
            Separator argumentsSeparator = new Separator();
            argumentsSeparator.setPadding(separatorInsets);
            manualVBox.getChildren().add(argumentsSeparator);
        }

        // Create the examples title
        Text examplesTitle = new Text("Examples:");
        examplesTitle.setFont(titleFont);
        manualVBox.getChildren().add(examplesTitle);

        // Create the examples element
        Text examples = new Text(commandManual.getExamples());
        examples.setWrappingWidth(paneWidth);
        manualVBox.getChildren().add(examples);

        // Create a separator
        Separator examplesSeparator = new Separator();
        examplesSeparator.setPadding(separatorInsets);
        manualVBox.getChildren().add(examplesSeparator);

        // Create the add command button
        Button addCommand = new Button("Use this command");
        addCommand.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextField inputField =
                        TranscriptSceneController.getInstance().getInputField();

                inputField.clear();
                inputField.requestFocus();

                inputField.setText(commandManual.getQuickAdd());
                inputField.positionCaret(commandManual.getQuickAdd().length() - 1);
            }
        });
        manualVBox.getChildren().add(addCommand);
    }
}
