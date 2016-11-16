package seng302.command.tutor;

import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the chord spelling tutor.
 */
public class ChordSpellingTutorTest {

    private Environment env = new Environment();
    private List<String> arguments;
    private String invalidArguments = "[ERROR] "
            + Error.SPELLINGTUTORINVALIDARGUMENTS.getError().replaceAll("\\r|\\n", "");

    @Test
    public void invalidQuestionNumber() {
        arguments = Arrays.asList("x-5");

        new ChordSpellingTutor(arguments).execute(env);

        assertEquals(invalidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void noValidString() {
        arguments = Arrays.asList("invalid");

        new ChordSpellingTutor(arguments).execute(env);

        assertEquals(invalidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void aFewIncorrectStrings() {
        arguments = Arrays.asList("invalid", "bad", "fail");

        new ChordSpellingTutor(arguments).execute(env);

        assertEquals(invalidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void validQuestionButBadString() {
        arguments = Arrays.asList("x5", "invalid", "bad", "fail");

        new ChordSpellingTutor(arguments).execute(env);

        assertEquals(invalidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void repeatedFlag() {
        arguments = Arrays.asList("x5", "randomNotes", "randomNotes");

        new ChordSpellingTutor(arguments).execute(env);

        assertEquals(invalidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void zeroQuestions() {
        arguments = Arrays.asList("x0");

        new ChordSpellingTutor(arguments).execute(env);

        assertEquals(invalidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

}