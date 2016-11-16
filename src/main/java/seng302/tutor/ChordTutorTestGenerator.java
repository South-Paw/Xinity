package seng302.tutor;

import seng302.controllers.chordtutor.ChordTutorSceneController;
import seng302.util.NoteUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Used to handle the logic for tutor questions.
 *
 * @author wrs35, avh17
 */
public class ChordTutorTestGenerator extends Tutor {

    /**
     * Constructor for ChordTutorTestGenerator.
     */
    public ChordTutorTestGenerator() {
        this.tutorName = "Chord Tutor";
        this.tutorType = TutorType.CHORD;
        this.tutorSceneController = ChordTutorSceneController.getInstance();
    }

    /**
     * Used to populate the chord questions array.
     *
     * @param numberOfQuestions The amount of questions to add.
     * @return The question list.
     */
    public ArrayList<List<String>> generateQuestions(Integer numberOfQuestions) {
        ArrayList<List<String>> questionsList = new ArrayList<>();
        Integer numberOfChordTypes = 8;
        Random random = new Random();

        for (Integer i = 0; i < numberOfQuestions; i++) {

            Integer typeNumber = random.nextInt(numberOfChordTypes);
            // 12 number of chromatic notes + 60 to middle c (c4)
            Integer noteNumber = random.nextInt(12) + 60;

            switch (typeNumber) {
                case 0:
                    questionsList.add(Arrays.asList(
                            "Major", NoteUtil.convertToNote(noteNumber)));
                    break;
                case 1:
                    questionsList.add(Arrays.asList(
                            "Minor", NoteUtil.convertToNote(noteNumber)));
                    break;
                case 2:
                    questionsList.add(Arrays.asList(
                            "Major Seventh", NoteUtil.convertToNote(noteNumber)));
                    break;
                case 3:
                    questionsList.add(Arrays.asList(
                            "Minor Seventh", NoteUtil.convertToNote(noteNumber)));
                    break;
                case 4:
                    questionsList.add(Arrays.asList(
                            "Seventh", NoteUtil.convertToNote(noteNumber)));
                    break;
                case 5:
                    questionsList.add(Arrays.asList(
                            "Half Diminished", NoteUtil.convertToNote(noteNumber)));
                    break;
                case 6:
                    questionsList.add(Arrays.asList(
                            "Major Sixth", NoteUtil.convertToNote(noteNumber)));
                    break;
                case 7:
                    questionsList.add(Arrays.asList(
                            "Diminished Seventh", NoteUtil.convertToNote(noteNumber)));
                    break;
                default:
                    break;
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
        return "What chord type is the chord played with the starting note "
                + currentQuestion.get(1);
    }
}
