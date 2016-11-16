package seng302.controllers.scaletutor;

import seng302.command.tutor.ScaleTutor;
import seng302.controllers.commontutor.TutorSceneController;
import seng302.tutor.ScaleTutorTestGenerator;


/**
 * Controller for the Scale Tutor Scene Controller.
 *
 * @author ljm163
 */
public class ScaleTutorSceneController extends TutorSceneController {

    private static ScaleTutorSceneController scaleTutorSceneController;

    /**
     * Constructor for the Scale tutor scene controller.
     */
    public ScaleTutorSceneController() {
        scaleTutorSceneController = this;
        tutorQuestionFxmlFile = "/scenes/scaletutor/ScaleTutorQuestion.fxml";
        tutorPopUpFxmlFile = "/scenes/scaletutor/ScaleTutorPopUp.fxml";
        testButtonLabel = "New Scale Tutor Test";
    }

    /**
     * Static method for retrieving class instance.
     *
     * @return The class instance.
     */
    public static ScaleTutorSceneController getInstance() {
        return scaleTutorSceneController;
    }

    /**
     * Used to get the current tutor test object.
     *
     * @return The tutor test object.
     */
    protected ScaleTutorTestGenerator getTutorTest() {
        return ScaleTutor.getInstance().getTutor();
    }

    /**
     * Used to get the instance of the current question.
     *
     * @return The current question.
     */
    public ScaleQuestionController getQuestion() {
        return ScaleQuestionController.getInstance();
    }

    /**
     * This function creates a test object and adds it to the scroll pane.
     */
    @Override
    public void addQuestionToScene() {
        if (getTutorTest().getCurrentQuestion().get(2).equalsIgnoreCase("play")) {
            tutorQuestionFxmlFile = "/scenes/scaletutor/ScaleTutorQuestion.fxml";
            super.addQuestionToScene();
        } else {
            tutorQuestionFxmlFile = "/scenes/scaletutor/ScaleModeQuestion.fxml";
            super.addQuestionToScene();
            getQuestion().setQuestionText(getTutorTest().getCurrentQuestion().get(1));
        }
    }
}
