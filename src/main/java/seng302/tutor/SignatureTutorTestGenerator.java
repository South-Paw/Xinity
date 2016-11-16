package seng302.tutor;

import seng302.command.tutor.SignatureTutor;
import seng302.controllers.signaturetutor.SignatureTutorSceneController;
import seng302.util.SignatureUtil;
import seng302.util.enumerator.ScaleType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The signature tutor test object.
 */
public class SignatureTutorTestGenerator extends Tutor {

    // How the signature will be identified number or notes
    private String signatureSpecifier;
    // Scale type major, minor, or both
    private String scaleNameType;
    private String currentQuestionScaleNameType;

    /**
     * Constructor for signature tutor.
     */
    public SignatureTutorTestGenerator() {
        this.tutorName = "Signature Tutor";
        this.tutorType = TutorType.SIGNATURE;
        this.tutorSceneController = SignatureTutorSceneController.getInstance();
    }

    /**
     * Used to populate the questions array.
     *
     * @param numberOfQuestions The amount of questions to add.
     * @return The question list.
     */
    public ArrayList<List<String>> generateQuestions(Integer numberOfQuestions) {
        Integer questionsToGenerate = numberOfQuestions;
        this.signatureSpecifier = SignatureTutor.getInstance().getSignatureSpecifier();
        this.scaleNameType = SignatureTutor.getInstance().getScaleNameType();

        ArrayList<List<String>> questionsList = new ArrayList<>();

        for (Integer i = 0; i < questionsToGenerate; i++) {

            // Handle both
            if (scaleNameType.equalsIgnoreCase("Both")) {
                currentQuestionScaleNameType = (new Random().nextInt() < 0)
                        ? "Major" : "Minor";
            } else {
                currentQuestionScaleNameType = scaleNameType;
            }

            // Determine question type
            boolean isType1Question = (new Random().nextInt() < 0);

            ScaleType currScaleType = ScaleType.fromString(
                    currentQuestionScaleNameType);

            // Allow up to 7 accidentals with ceiling of 8, key signatures
            // have no more than 7 accidentals
            Integer numberofAccidentals = (int)(Math.random() * 8);
            String accidental = (new Random().nextInt() < 0)
                    ? "#" : "b";
            String keySignature = numberofAccidentals.toString() + accidental;
            String scaleNameAnswer = SignatureUtil.determineScaleFromKeySignature(keySignature,
                    currScaleType);

            // Convert to notes if needed
            if (signatureSpecifier.equalsIgnoreCase("notes")) {
                List<String> keySignatureOrder = SignatureUtil.getKeySignatureOrder(accidental);
                if (keySignatureOrder != null) {
                    keySignature = "";
                    for (Integer j = 0; j < numberofAccidentals; j++) {
                        keySignature += keySignatureOrder.get(j) + accidental + ", ";
                    }
                    if (keySignature.length() > 2) {
                        keySignature = keySignature.substring(0, keySignature.length() - 2);
                    } else {
                        keySignature = "No accidental notes";
                    }
                }
            }

            if (!keySignature.equals("No accidental notes")) {
                // Add question with correct format
                if (isType1Question) {
                    String questionString = "What is the scale relating to the key signature "
                            + keySignature + " (" + currentQuestionScaleNameType + ")?";
                    questionsList.add(Arrays.asList(scaleNameAnswer + " "
                            + currentQuestionScaleNameType, questionString));
                } else { // Type 2 Question
                    String questionString = "What is the key signature of the scale "
                            + scaleNameAnswer + " " + currentQuestionScaleNameType + "?";
                    questionsList.add(Arrays.asList(keySignature, questionString));
                }
            } else {
                questionsToGenerate++;
            }
        }
        return questionsList;
    }

    /**
     * Generates the question string used in recording scores.
     *
     * @return the question string.
     */
    protected String getQuestionString() {
        return currentQuestion.get(1);
    }
}
