package seng302.tutor;

import seng302.command.tutor.ChordSpellingTutor;
import seng302.controllers.spellingtutor.SpellingTutorSceneController;
import seng302.util.ChordUtil;
import seng302.util.NoteUtil;
import seng302.util.enumerator.ChordQuality;
import seng302.util.enumerator.Inversion;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Tutor object for the spelling tutor.
 *
 * @author avh17.
 */
public class ChordSpellingTutorTestGenerator extends Tutor {

    private List<ChordQuality> chordTypes = new ArrayList<>();
    private Boolean noEnharmonic = false;
    private Boolean randomNotes = false;

    /**
     * Constructor for signature tutor.
     */
    public ChordSpellingTutorTestGenerator() {
        this.tutorName = "Spelling Tutor";
        this.tutorType = TutorType.SPELLING;
        this.tutorSceneController = SpellingTutorSceneController.getInstance();
    }

    /**
     * Used to populate the questions array.
     *
     * @param numberOfQuestions The amount of questions to add.
     * @return The question list.
     */
    public ArrayList<List<String>> generateQuestions(Integer numberOfQuestions) {
        Random random = new Random();
        ArrayList<List<String>> questionList = new ArrayList<>();
        this.chordTypes = ChordSpellingTutor.getInstance().getChordTypes();
        this.noEnharmonic = ChordSpellingTutor.getInstance().getNoEnharmonic();
        this.randomNotes = ChordSpellingTutor.getInstance().getRandomNotes();

        for (Integer i = 0; i < numberOfQuestions; i++) {
            // each question will have a calculated correct answer
            String question;
            String answer = "";

            // generates a random starting note
            String randomStartingNote = NoteUtil.convertToNote(random.nextInt(12) + 60);
            randomStartingNote = randomStartingNote.substring(0, randomStartingNote.length() - 1);

            // Selects a chord quality at random
            ChordQuality randomQuality = chordTypes.get(random.nextInt(chordTypes.size()));
            String randomStringQuality = randomQuality.getChordQualities().get(0);

            // Generates a number for the level of inversion
            Integer levelOfInversion;
            switch (randomQuality) {
                case MAJORTRIAD:
                case MINORTRIAD:
                case AUGMENTEDTRIAD:
                    levelOfInversion = random.nextInt(3); // triads have 3 notes
                    break;
                default:
                    levelOfInversion = random.nextInt(4);
                    break;
            }
            Inversion inversion = Inversion.fromString(levelOfInversion.toString());
            String inversionString = "";
            if (inversion != Inversion.ROOT) {
                inversionString = inversion.toString();
            }

            // create a random chord from random note and quality
            List<XinityNote> chordNotes;
            try {
                chordNotes = ChordUtil.chord(new XinityNote(randomStartingNote), randomQuality);
                if (chordNotes == null) {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }

            // this needs to be done before 'invertChord' otherwise method will affect chordNotes
            List<String> stringChordNotes = new ArrayList<>();
            for (XinityNote note : chordNotes) {
                stringChordNotes.add(note.getLetterAndAccidental()); // remove octave from note
            }

            // get appropriate inversion, 0 will be root position
            ChordUtil.invertChord(chordNotes, inversion);

            Integer questionType = 1 + random.nextInt(2);

            // question: chord name, answer: chord notes
            if (questionType == 1) {
                String randomChord = randomStartingNote + " " + randomStringQuality + " "
                        + inversionString;
                question = "Give the corresponding notes of: " + randomChord;

                for (XinityNote note : chordNotes) {
                    answer += note.getLetterAndAccidental() + ", ";
                }

                answer = answer.substring(0, answer.length() - 2); // remove extra comma and space
                questionList.add(Arrays.asList(answer, question));
            }

            // question: chord notes, answer: chord name(s)
            if (questionType == 2) {
                Boolean notesRandom = false;
                if (randomNotes) {
                    Integer randomOrNot = random.nextInt(2);
                    notesRandom = (randomOrNot == 0);
                }

                Set<String> chords;
                if (notesRandom) {
                    List<String> randomNotes = new ArrayList<>();
                    Integer numberOfNotes = 3 + random.nextInt(2);
                    for (Integer j = 0; j < numberOfNotes; j++) {
                        String note = NoteUtil.convertToNote(random.nextInt(12) + 60);
                        randomNotes.add(note.substring(0, note.length() - 1)); // remove octave
                    }
                    String randomNotesString = randomNotes.toString();
                    randomNotesString = randomNotesString.substring(
                            1, randomNotesString.length() - 1); // remove brackets
                    question = "Give the chord name of the notes: " + randomNotesString;
                    chords = ChordUtil.findChord(randomNotes, false);
                } else {
                    String notesString = stringChordNotes.toString();
                    notesString = notesString.substring(1, notesString.length() - 1);
                    question = "Give the chord name of the notes: " + notesString;
                    chords = ChordUtil.findChord(stringChordNotes, false);
                }
                if (chords != null) {
                    if (noEnharmonic) {
                        answer = chords.iterator().next(); // get first element of LinkedHashMap
                    } else {
                        answer = chords.toString();
                        answer = answer.substring(1, answer.length() - 1); // remove brackets
                    }
                } else {
                    answer = "No Chord";
                }
                questionList.add(Arrays.asList(answer, question));
            }
        }
        return questionList;
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
