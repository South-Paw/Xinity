package seng302.controllers.about;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller for the DSL Reference Card Scene.
 *
 * @author adg62
 */
public class AboutSceneController {

    @FXML Label groupMembers;

    private static AboutSceneController aboutSceneController;

    /** Constructor for the class. */
    public AboutSceneController() {
        aboutSceneController = this;
    }

    /**
     * Static method to get the class instance.
     *
     * @return This class instance.
     */
    public static AboutSceneController getInstance() {
        return aboutSceneController;
    }

    /**
     * Method for loading the about scene.
     */
    public void loadAboutScene() {
        groupMembers.setText("Adam Hunt\nAlex Gabites\nJono Smythe\nLiam McKee\nMathew Jensen\n"
                + "Peter Roger\nWilliam Scott");
    }
}
