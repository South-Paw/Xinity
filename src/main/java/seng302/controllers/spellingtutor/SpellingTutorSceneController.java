package seng302.controllers.spellingtutor;

import seng302.command.tutor.ChordSpellingTutor;
import seng302.controllers.commontutor.TutorSceneController;
import seng302.tutor.ChordSpellingTutorTestGenerator;

/**
 * Controller class for the spelling tutor.
 *
 * @author avh17.
 */
public class SpellingTutorSceneController extends TutorSceneController {

    private static SpellingTutorSceneController spellingTutorSceneController;

    /**
     * Constructor for class.
     */
    public SpellingTutorSceneController() {
        spellingTutorSceneController = this;
        tutorQuestionFxmlFile = "/scenes/spellingtutor/SpellingTutorQuestion.fxml";
        tutorPopUpFxmlFile = "/scenes/spellingtutor/SpellingTutorPopUp.fxml";
        testButtonLabel = "New Spelling Tutor Test";
    }

    /**
     * Static getter method for the class instance.
     *
     * @return The class instance.
     */
    public static SpellingTutorSceneController getInstance() {
        return spellingTutorSceneController;
    }

    /**
     * Overrides the add question to scene function and adds some question text.
     */
    @Override
    public void addQuestionToScene() {
        super.addQuestionToScene();
        getQuestion().setQuestionText(getTutorTest().getCurrentQuestion().get(1));
    }

    /**
     * Used to get the current tutor test object.
     *
     * @return The tutor test object.
     */
    protected ChordSpellingTutorTestGenerator getTutorTest() {
        return ChordSpellingTutor.getInstance().getTutor();
    }

    /**
     * Used to get the instance of the current question.
     *
     * @return The current question.
     */
    public SpellingQuestionController getQuestion() {
        return SpellingQuestionController.getInstance();
    }
}
