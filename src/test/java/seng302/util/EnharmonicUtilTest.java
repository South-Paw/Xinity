package seng302.util;

import org.junit.Test;
import seng302.util.object.XinityNote;

import static org.junit.Assert.*;

/**
 * Tests for the enharmonic utils class.
 *
 * @author avh17.
 */
public class EnharmonicUtilTest {

    @Test
    public void testCDown() throws Exception {
        XinityNote test = new XinityNote("C2");
        XinityNote returned = EnharmonicUtil.findEnharmonicEquivalent("-", test);
        String expected = "B#1";
        assertEquals(expected, returned.getNote());
    }

    @Test
    public void testCUp() throws Exception {
        XinityNote test = new XinityNote("C2");
        XinityNote returned = EnharmonicUtil.findEnharmonicEquivalent("+", test);
        String expected = "Dbb2";
        assertEquals(expected, returned.getNote());
    }

    @Test
    public void testBdown() throws Exception {
        XinityNote test = new XinityNote("B5");
        XinityNote returned = EnharmonicUtil.findEnharmonicEquivalent("-", test);
        String expected = "Ax5";
        assertEquals(expected, returned.getNote());
    }

    @Test
    public void testBUp() throws Exception {
        XinityNote test = new XinityNote("B4");
        XinityNote returned = EnharmonicUtil.findEnharmonicEquivalent("+", test);
        String expected = "Cb5";
        assertEquals(expected, returned.getNote());
    }

    @Test
    public void testFlatUp() throws Exception {
        XinityNote test = new XinityNote("Ab4");
        XinityNote returned = EnharmonicUtil.findEnharmonicEquivalent("+", test);
        String expected = null;
        assertEquals(expected, returned);
    }

    @Test
    public void testDoubleFlatUp() throws Exception {
        XinityNote test = new XinityNote("Dbb");
        XinityNote returned = EnharmonicUtil.findEnharmonicEquivalent("+", test);
        String expected = null;
        assertEquals(expected, returned);
    }

    @Test
    public void testDoubleDown() throws Exception {
        XinityNote test = new XinityNote("Dbb");
        XinityNote returned = EnharmonicUtil.findEnharmonicEquivalent("-", test);
        String expected = "C4";
        assertEquals(expected, returned.getNote());
    }

    @Test
    public void testSharpUp() throws Exception {
        XinityNote test = new XinityNote("C#4");
        XinityNote returned = EnharmonicUtil.findEnharmonicEquivalent("+", test);
        String expected = "Db4";
        assertEquals(expected, returned.getNote());
    }

    @Test
    public void testDoubleSharpUp() throws Exception {
        XinityNote test = new XinityNote("Cx4");
        XinityNote returned = EnharmonicUtil.findEnharmonicEquivalent("+", test);
        String expected = "D4";
        assertEquals(expected, returned.getNote());
    }

    @Test
    public void testLowerCaseNote() throws Exception {
        XinityNote test = new XinityNote("a");
        XinityNote returned = EnharmonicUtil.findEnharmonicEquivalent("+", test);
        String expected = "Bbb4";
        assertEquals(expected, returned.getNote());
    }
}