package seng302.controllers.chordtutor;

import seng302.command.tutor.ChordTutor;
import seng302.controllers.commontutor.TutorQuestionController;
import seng302.controllers.commontutor.TutorSceneController;
import seng302.tutor.Tutor;

/**
 * Controller class for the chord tutor scene.
 *
 * @author avh17, wrs35
 */
public class ChordTutorSceneController extends TutorSceneController {

    private static ChordTutorSceneController chordTutorSceneController;

    /**
     * Constructor for class.
     */
    public ChordTutorSceneController() {
        chordTutorSceneController = this;
        tutorQuestionFxmlFile = "/scenes/chordtutor/ChordTutorQuestion.fxml";
        tutorPopUpFxmlFile = "/scenes/chordtutor/ChordTutorPopUp.fxml";
        testButtonLabel = "New Chord Test";
    }

    /**
     * Static getter method for the class instance.
     *
     * @return The class instance.
     */
    public static ChordTutorSceneController getInstance() {
        return chordTutorSceneController;
    }

    /**
     * Used to get the tutor object.
     *
     * @return The tutor object.
     */
    protected Tutor getTutorTest() {
        return ChordTutor.getInstance().getTutor();
    }

    /**
     * Used to get the instance of the question controller.
     *
     * @return The question controller instance.
     */
    public ChordQuestionController getQuestion() {
        return ChordQuestionController.getInstance();
    }

    /**
     * Overrides the add question to scene function and adds some question text.
     */
    @Override
    public void addQuestionToScene() {
        String questionType = getTutorTest().getCurrentQuestion().get(2);
        String question = getTutorTest().getCurrentQuestion().get(1);
        if (questionType.equalsIgnoreCase("play")) {
            tutorQuestionFxmlFile = "/scenes/chordtutor/ChordTutorQuestion.fxml";
            super.addQuestionToScene();
        } else if (questionType.equalsIgnoreCase("diatonic")) {
            tutorQuestionFxmlFile = "/scenes/chordtutor/ChordDiatonicQuestion.fxml";
            super.addQuestionToScene();
            getQuestion().setQuestionText(question);
        }
    }
}
