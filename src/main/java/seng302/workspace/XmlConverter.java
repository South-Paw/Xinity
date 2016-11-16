package seng302.workspace;

import javafx.scene.control.Alert;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import seng302.util.ConfigUtil;
import seng302.util.TempoUtil;
import seng302.util.TermsUtil;
import seng302.util.object.Term;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * This class is used to saveProject and load the xml data to and from the system.
 */
public class XmlConverter {

    /**
     * Protected constructor.
     */
    protected XmlConverter() {}

    /**
     * Loads term data from the given file.
     *
     * @param file The file containing the term data.
     */
    protected void loadTermDataFromFile(File file) throws Exception {
        //Load the XML file and create a document object
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList termList = doc.getElementsByTagName("Term");

        for (Integer i = 0; i < termList.getLength(); i++) {
            Node node = termList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) node;

                TermsUtil.addTerm(
                        element.getElementsByTagName("Name").item(0).getTextContent(),
                        element.getElementsByTagName("Language").item(0).getTextContent(),
                        element.getElementsByTagName("Description").item(0).getTextContent(),
                        element.getElementsByTagName("Category").item(0).getTextContent());
            }
        }
    }

    /**
     * Saves the current terms to the specified file.
     *
     * @param projectFile The file to saveProject to.
     */
    protected void saveTermDataToFile(File projectFile) {
        Collection<Term> values = Project.getInstance().viewTermDictionary().values();

        File file = new File(projectFile,"/terms");

        if (!(file.exists() && file.isDirectory())) {
            new File(projectFile,"/terms").mkdir();
        }

        try {
            Element root = new Element("TermDictionary");

            for (Term term: values) {
                Element child1 = new Element("Name");
                child1.addContent(term.getName());

                Element child2 = new Element("Language");
                child2.addContent(term.getLanguage());

                Element child3 = new Element("Description");
                child3.addContent(term.getDescription());

                Element child4 = new Element("Category");
                child4.addContent(term.getCategory());

                Element termElement = new Element("Term");

                termElement.addContent(child1);
                termElement.addContent(child2);
                termElement.addContent(child3);
                termElement.addContent(child4);

                root.addContent(termElement);
            }

            org.jdom2.Document doc = new org.jdom2.Document();
            doc.setRootElement(root);

            XMLOutputter outPutter = new XMLOutputter();
            outPutter.setFormat(Format.getPrettyFormat());
            outPutter.output(doc, new FileWriter(new File(file.getPath() + "/terms.xml")));

        } catch (Exception e) {
            e.printStackTrace();
            alert("Save", file);
        }
    }

    /**
     * This loads the app variables into the system.
     *
     * @param file The file to read from.
     */
    protected void loadAppVariables(File file) throws Exception {
        //Load the XML file and create a document object
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(file);
        doc.getDocumentElement().normalize();

        //Get the tempo from the file and update the system
        String tempoString = doc.getElementsByTagName("Tempo").item(0).getTextContent();
        TempoUtil.setTempo(Integer.valueOf(tempoString));
    }

    /**
     * This save the add data to an xml file.
     *
     * @param directory The file to saveProject to.
     * @return True if successfully saved. False if unsuccessful.
     */
    protected Boolean saveAppVariables(File directory) {
        try {
            Element child1 = new Element("Version");
            child1.addContent(ConfigUtil.retrieveVersion());
            Element child2 = new Element("Tempo");
            child2.addContent(TempoUtil.getTempo().toString());

            Element root = new Element("Data");
            root.addContent(child1);
            root.addContent(child2);

            org.jdom2.Document doc = new org.jdom2.Document();
            doc.setRootElement(root);

            XMLOutputter outPutter = new XMLOutputter();
            outPutter.setFormat(Format.getPrettyFormat());
            outPutter.output(doc, new FileWriter(new File(directory.getPath() + "/meta.xml")));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            alert("Save", directory);
            return false;
        }
    }

    /**
     * Used to display an error dialog for loading and saving.
     *
     * @param saveLoad A string used to determine the error type.
     * @param file The file relating to the error.
     */
    private void alert(String saveLoad, File file) {
        String header;
        String context;

        //Set the error messages
        if (saveLoad.equals("Save")) {
            header = "Could not save Project!";
            context = "Could not save data to file:\n" + file.getPath();
        } else {
            header = "Could not load Project!";
            context = "Could not load data from file:\n" + file.getPath();
        }

        //Display the alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

}
