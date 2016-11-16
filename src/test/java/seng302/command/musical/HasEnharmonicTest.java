package seng302.command.musical;

import org.junit.Test;
import seng302.Environment;
import seng302.command.musical.HasEnharmonic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Has Enharmonic Command tests.
 *
 * @author wrs35.
 */
public class HasEnharmonicTest {

    private Environment env = new Environment();

    @Test
    public void testNoteWithoutEnharmonic() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("A");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic1() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("D");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic2() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("G");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic3() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("a");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic4() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("d");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic5() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("g");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic6() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("A0");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic7() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("D1");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic8() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("G2");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic9() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("a3");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic10() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("d4");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic11() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("g5");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic12() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("a6");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic13() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("d7");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithoutEnharmonic14() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("g8");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note does not"));
    }

    @Test
    public void testNoteWithEnharmonic() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("A#");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic1() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("Db");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic2() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("G#");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic3() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("ab");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic4() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("d#");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic5() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("gb");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic6() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("A#0");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic7() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("Db1");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic8() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("G#2");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic9() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("ab3");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic10() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("b4");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic11() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("c5");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic12() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("e6");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic13() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("f7");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }

    @Test
    public void testNoteWithEnharmonic14() throws Exception {
        List<String> note = new ArrayList<>();
        note.add("f#8");
        new HasEnharmonic(note).execute(env);
        assertTrue(env.getOutput().contains("Note has"));
    }
}




