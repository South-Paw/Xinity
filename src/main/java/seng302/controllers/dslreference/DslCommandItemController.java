package seng302.controllers.dslreference;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * Controller class for a dsl command item.
 *
 * @author avh17
 */
public class DslCommandItemController {

    private static DslCommandItemController dslCommandItemController;

    @FXML private Text itemText;

    /**
     * Constructor for the class.
     */
    public DslCommandItemController() {
        dslCommandItemController = this;
    }

    /**
     * Used to get the class instance.
     *
     * @return The class instance.
     */
    public static DslCommandItemController getInstance() {
        return dslCommandItemController;
    }

    /**
     * Used to set the item text.
     *
     * @param text The text.
     */
    public void setItemText(String text) {
        itemText.setText(text);
    }

    /**
     * Handles the item being clicked.
     *
     * @param event the click event.
     */
    @FXML
    public void itemClicked(MouseEvent event) {
        DslReferenceSceneController.getInstance().setCommandItem(itemText.getText());
    }
}
