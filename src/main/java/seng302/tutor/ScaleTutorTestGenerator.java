package seng302.tutor;

import seng302.command.tutor.ScaleTutor;
import seng302.controllers.scaletutor.ScaleTutorSceneController;
import seng302.util.NoteUtil;
import seng302.util.ScalesUtil;
import seng302.util.enumerator.ScaleDirection;
import seng302.util.enumerator.ScaleGroup;
import seng302.util.enumerator.ScaleMode;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Test set generator for the scale tutor.
 */
public class ScaleTutorTestGenerator extends Tutor {

    /**
     * Class constructor.
     */
    public ScaleTutorTestGenerator() {
        this.tutorName = "Scale Tutor";
        this.tutorType = TutorType.SCALE;
        this.tutorSceneController = ScaleTutorSceneController.getInstance();
    }

    /**
     * Generates a set of questions.
     *
     * @param numberOfQuestions The amount of questions to add.
     * @return A set of questions.
     */
    public ArrayList<List<String>> generateQuestions(Integer numberOfQuestions) {
        ArrayList<List<String>> questionsList = new ArrayList<>();
        List<String> acceptableScales = new ArrayList<>();
        Random random = new Random();

        for (ScaleType scaleType : ScaleGroup.NATURAL.getGroup()) {
            acceptableScales.add(scaleType.toString());
        }

        for (ScaleType scaleType : ScaleGroup.MELODIC.getGroup()) {
            acceptableScales.add(scaleType.toString());
        }

        for (ScaleType scaleType : ScaleGroup.PENTATONIC.getGroup()) {
            acceptableScales.add(scaleType.toString());
        }

        for (ScaleType scaleType : ScaleGroup.JAZZ.getGroup()) {
            acceptableScales.add(scaleType.toString());
        }

        // Check Harmonic Flag
        if (ScaleTutor.getInstance().includeHarmonic()) {
            for (ScaleType scaleType : ScaleGroup.HARMONIC.getGroup()) {
                acceptableScales.add(scaleType.toString());
            }

            for (ScaleType scaleType : ScaleGroup.HARMONICMINORMODES.getGroup()) {
                acceptableScales.add(scaleType.toString());
            }
        }

        // Check for Modes
        if (ScaleTutor.getInstance().includeModes()) {

            for (ScaleType scaleType : ScaleGroup.MAJORMODES.getGroup()) {
                acceptableScales.add(scaleType.toString());
            }

            for (ScaleType scaleType : ScaleGroup.MELODICMINORMODES.getGroup()) {
                acceptableScales.add(scaleType.toString());
            }

            acceptableScales.remove("Minormajor");
            acceptableScales.remove("Ionian");
        }


        for (Integer i = 0; i < numberOfQuestions; i++) {
            Integer modeInteger = random.nextInt(2);

            if (modeInteger == 0 && ScaleTutor.getInstance().includeModes()) {
                // Generate a note in middle C
                XinityNote randomNote = NoteUtil.randomNote(60, 72);

                ScaleType questionScaleType;
                List<XinityNote> scale;
                if (random.nextInt() % 2 == 0) {
                    questionScaleType = ScaleType.MAJOR;
                    scale = ScalesUtil.buildPlayingScale(randomNote,
                        questionScaleType, ScaleDirection.UP, 1);
                } else {
                    questionScaleType = ScaleType.MELODICMINOR;
                    scale = ScalesUtil.buildPlayingScale(randomNote,
                        questionScaleType, ScaleDirection.UP, 1);
                }


                Integer randomIndexOne = random.nextInt(scale.size() - 1);
                Integer randomIndexTwo = random.nextInt(scale.size() - 1);

                while (randomIndexOne.equals(randomIndexTwo)) {
                    randomIndexTwo = random.nextInt(scale.size() - 1);
                }

                ScaleMode modeOne;
                ScaleMode modeTwo;
                if (questionScaleType == ScaleType.MAJOR) {
                    modeOne = ScaleMode.fromDegreeMajor(randomIndexOne + 1);
                    modeTwo = ScaleMode.fromDegreeMajor(randomIndexTwo + 1);
                } else { // Melodic Minor
                    modeOne = ScaleMode.fromDegreeMelodicMinor(randomIndexOne + 1);
                    modeTwo = ScaleMode.fromDegreeMelodicMinor(randomIndexTwo + 1);
                }


                String scaleNameOne = scale.get(randomIndexOne).getLetterAndAccidental() + " "
                    + modeOne.toString();

                String scaleNameTwo = scale.get(randomIndexTwo).getLetterAndAccidental() + " "
                    + modeTwo.toString();

                String question = String.format("What is the parent of the scale %s and %s",
                    scaleNameOne, scaleNameTwo);

                String answer = randomNote.getLetterAndAccidental() + " "
                    + questionScaleType.toString();

                questionsList.add(Arrays.asList(answer, question, "mode"));
            } else {
                // Get a random note and scale type. Notes are best between 36 and 96
                String randomNote = NoteUtil.randomNote(36, 96).getNote();
                String name = acceptableScales.get(random.nextInt(acceptableScales.size()));

                // Add the question to the question set
                questionsList.add(Arrays.asList(name, randomNote, "play"));
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
        return "Which scale type does the scale with the starting note "
                + currentQuestion.get(1) + " belong to?";
    }
}
