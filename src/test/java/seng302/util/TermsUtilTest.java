package seng302.util;

import org.junit.Before;
import org.junit.Test;
import seng302.workspace.Project;

import static junit.framework.TestCase.assertEquals;

/**
 * @author adg62
 */
public class TermsUtilTest {
    @Before
    public void setUp() {
        TermsUtil.clearAllTerms();
    }

    @Test
    public void addingValidTermWorks() throws Exception {
        TermsUtil.addTerm("A Term", "A Language", "A Meaning", "A Category");
        assertEquals(1, Project.getInstance().viewTermDictionary().size());
    }

    @Test
    public void addingMultipleValidTermWorks() throws Exception {
        TermsUtil.addTerm("A Term", "A Language", "A Meaning", "A Category");

        assertEquals(1, Project.getInstance().viewTermDictionary().size());

        TermsUtil.addTerm("B Term", "A Language", "A Meaning", "A Category");
        assertEquals(2, Project.getInstance().viewTermDictionary().size());
    }

    @Test
    public void addingSameTermTwice() throws Exception {
        TermsUtil.addTerm("A Term", "A Language", "A Meaning", "A Category");
        assertEquals(1, Project.getInstance().viewTermDictionary().size());

        TermsUtil.addTerm("A Term", "Aaaa Language", "A Meaning", "A Category");
        assertEquals(1, Project.getInstance().viewTermDictionary().size());
    }

    @Test
    public void removeAddedTerm() throws Exception {
        TermsUtil.addTerm("A54 Term", "A Language", "A Meaning", "A Category");
        assertEquals(1, Project.getInstance().viewTermDictionary().size());

        TermsUtil.removeTerm("A54 Term");
        assertEquals(0, Project.getInstance().viewTermDictionary().size());
    }

    @Test
    public void removeNotAddedTerm() throws Exception {
        TermsUtil.addTerm("A7888 Term", "A Language", "A Meaning", "A Category");
        assertEquals(1, Project.getInstance().viewTermDictionary().size());

        TermsUtil.removeTerm("B Term");
        assertEquals(1, Project.getInstance().viewTermDictionary().size());
    }


    @Test
    public void clearTermsDict() throws Exception {
        TermsUtil.addTerm("A213 Term", "A Language", "A Meaning", "A Category");
        TermsUtil.addTerm("B53 Term", "A Language", "A Meaning", "A Category");
        assertEquals(2, Project.getInstance().viewTermDictionary().size());

        TermsUtil.clearAllTerms();

        assertEquals(0, Project.getInstance().viewTermDictionary().size());
    }

    @Test
    public void testReadTerm() throws Exception {
        TermsUtil.addTerm("Term dfkjs", "A Language", "A Meaning", "A Category");

        assertEquals("Term dfkjs", TermsUtil.readTerm("Term dfkjs").getName());
        assertEquals("A Language", TermsUtil.readTerm("Term dfkjs").getLanguage());
        assertEquals("A Meaning", TermsUtil.readTerm("Term dfkjs").getDescription());
        assertEquals("A Category", TermsUtil.readTerm("Term dfkjs").getCategory());
    }

    @Test
    public void testReadTermAttribute() throws Exception {
        TermsUtil.addTerm("Term Aasd", "Aasd Language", "A Meaning", "A Category");

        assertEquals("Aasd Language", TermsUtil.readTerm("Term Aasd").getLanguage());
    }
}