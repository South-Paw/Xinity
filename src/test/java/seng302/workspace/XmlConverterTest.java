package seng302.workspace;

import org.junit.*;
import org.junit.rules.TemporaryFolder;
import seng302.util.TermsUtil;
import seng302.util.object.Term;

import java.io.File;
import java.io.PrintWriter;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by plr37 on 16/07/16.
 */
public class XmlConverterTest {

/*    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() {
        TermsUtil.clearAllTerms();
    }

    @Test
    public void loadTermDataFromFileTest1() throws Exception {

        File file = folder.newFile("testFile.xml");
        PrintWriter writer = new PrintWriter(file);
        writer.write(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<TermDictionary>\n" +
                "  <Term>\n" +
                "    <Name>a</Name>\n" +
                "    <Language>b</Language>\n" +
                "    <Description>gafds</Description>\n" +
                "    <Category>d</Category>\n" +
                "  </Term>\n" +
                "  <Term>\n" +
                "    <Name>e</Name>\n" +
                "    <Language>f</Language>\n" +
                "    <Description>g</Description>\n" +
                "    <Category>h</Category>\n" +
                "  </Term>\n" +
                "</TermDictionary>"
        );

        XmlConverter converter = new XmlConverter();
        converter.loadTermDataFromFile(file);

        Term a = new Term("a", "b", "c", "d");

        assertEquals(TermsUtil.readTerm(a.getName()), a);
    }

    @Ignore
    @Test
    public void loadTermDataFromFileTest2() throws Exception {
        PrintWriter file = new PrintWriter(folder.newFile("testFile.xml"));
        file.write(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<TermDictionary>\n" +
                        "  <Term>\n" +
                        "    <Name>a</Name>\n" +
                        "    <Language>b</Language>\n" +
                        "    <Description>c</Description>\n" +
                        "    <Category>d</Category>\n" +
                        "  </Term>\n" +
                        "  <Term>\n" +
                        "    <Name>e</Name>\n" +
                        "    <Language>f</Language>\n" +
                        "    <Description>g</Description>\n" +
                        "    <Category>h</Category>\n" +
                        "  </Term>\n" +
                        "</TermDictionary>"
        );

        Term e = new Term();
        e.setName("e");
        e.setLanguage("f");
        e.setDescription("g");
        e.setCategory("h");
        assertEquals(TermsUtil.readTerm(e.getName()), e);
        TermsUtil.clearAllTerms();

    }


    @Ignore
    @Test
    public void loadTermDataFromFileTest2() throws Exception {
        PrintWriter file = new PrintWriter(folder.newFile("testFile.xml"));
        file.write(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<TermDictionary>\n" +
                        "  <Term>\n" +
                        "    <Name>a</Name>\n" +
                        "    <Language>b</Language>\n" +
                        "    <Description>c</Description>\n" +
                        "    <Category>d</Category>\n" +
                        "  </Term>\n" +
                        "  <Term>\n" +
                        "    <Name>e</Name>\n" +
                        "    <Language>f</Language>\n" +
                        "    <Description>g</Description>\n" +
                        "    <Category>h</Category>\n" +
                        "  </Term>\n" +
                        "</TermDictionary>"
        );

        Term e = new Term();
        e.setName("e");
        e.setLanguage("f");
        e.setDescription("g");
        e.setCategory("h");
        assertEquals(TermsUtil.readTerm(e.getName()), e);
        TermsUtil.clearAllTerms();
    }*/
}