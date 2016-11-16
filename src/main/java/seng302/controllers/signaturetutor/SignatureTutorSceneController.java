package seng302.controllers.signaturetutor;

import seng302.command.tutor.SignatureTutor;
import seng302.controllers.commontutor.TutorSceneController;
import seng302.tutor.SignatureTutorTestGenerator;

/**
 * Controller class for the signature tutor scene.
 */
public class SignatureTutorSceneController extends TutorSceneController {

    private static SignatureTutorSceneController signatureTutorSceneController;

    /**
     * Constructor for class.
     */
    public SignatureTutorSceneController() {
        signatureTutorSceneController = this;
        tutorQuestionFxmlFile = "/scenes/signaturetutor/SignatureTutorQuestion.fxml";
        tutorPopUpFxmlFile = "/scenes/signaturetutor/SignatureTutorPopUp.fxml";
        testButtonLabel = "New Signature Tutor Test";
    }

    /**
     * Static getter method for the class instance.
     *
     * @return The class instance.
     */
    public static SignatureTutorSceneController getInstance() {
        return signatureTutorSceneController;
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
    protected SignatureTutorTestGenerator getTutorTest() {
        return SignatureTutor.getInstance().getTutor();
    }

    /**
     * Used to get the instance of the current question.
     *
     * @return The current question.
     */
    public SignatureQuestionController getQuestion() {
        return SignatureQuestionController.getInstance();
    }
}
