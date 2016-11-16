package seng302.workspace;

import javafx.scene.control.Alert;

import seng302.controllers.MainSceneController;
import seng302.util.object.Term;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Helps maintain, saveProject, and create new Projects.
 *
 * @author  plr37, avh17, adg62
 */
public class Project {
    private static Project instance = null;

    private String projectName;
    private File fileLocation;

    private Boolean isNew;
    private Boolean isDirty;

    /** Tempo that the application is running at. */
    private Integer tempo = 120;

    /** Crotchet duration (in milliseconds) calculated from tempo. */
    private Integer crotchet = Math.round((float) 60000 / (float) tempo);

    /** The split variables for the swing quaver duration. */
    private Float swingFirstSplit;
    private Float swingSecondSplit;

    /** The in memory term dictionary. */
    private Map<String, Term> termDictionary = new HashMap<>();

    /**
     * Singleton constructor.
     */
    private Project() {
        createNewProject();
    }

    /**
     * Creates a new instance of the Project if one does not exist. Returns that instance if one has
     * already has been created.
     *
     * @return The instance of the Project.
     */
    public static Project getInstance() {
        if (instance == null) {
            instance = new Project();
        }

        return instance;
    }

    /**
     * Change the project instance to a new state.
     */
    public void createNewProject() {
        resetTempo();
        resetTerms();

        projectName = "Untitled Project";
        fileLocation = null;

        isNew = true;
        isDirty = false;
    }

    /**
     * Get the project's name as a String.
     * 
     * @return The current project's name.
     */
    public String getProjectName() {
        return projectName;
    }
    
    /**
     * Get's the project path without the project folder name on the end.
     *
     * @return The path to the folder that this project is stored in.
     */
    public String getProjectPath() {
        Integer pathLength = fileLocation.getPath().length() - projectName.length();
        
        return fileLocation.getPath().substring(0, pathLength - 1);
    }

    /**
     * Return if the project is newly created.
     *
     * @return True if new project, false if not.
     */
    public Boolean isNew() {
        return isNew;
    }

    /**
     * Return if the project has changes.
     *
     * @return True if changes made, false if not.
     */
    public Boolean isDirty() {
        return isDirty;
    }

    /**
     * Gets the current state of the workspace and updates isDirty.
     */
    public void markDirty() {
        isDirty = true;

        MainSceneController.setProjectWindowTitleAsDirty();
    }
    
    /**
     * Get the current tempo of the project.
     * 
     * @return The current tempo.
     */
    public Integer getTempo() {
        return tempo;
    }

    /**
     * Update the tempo and crotchet of the project.
     * 
     * @param newTempo New Tempo value.
     */
    public void setTempo(Integer newTempo) {
        this.tempo = newTempo;
        updateCrotchet();
        this.swingFirstSplit = ((float) 2 / (float) 3) * crotchet;
        this.swingSecondSplit = ((float) 1 / (float) 3) * crotchet;

        markDirty();
    }
    
    /**
     * Reset the tempo and crochet to their defaults.
     */
    public void resetTempo() {
        if (tempo != 120) {
            markDirty();
        }
        
        tempo = 120;
        updateCrotchet();
    }

    /**
     * Private setter to update crotchet duration after each change to
     * the tempo. Method added to avoid duplicate code when setting the crotchet duration.
     */
    private void updateCrotchet() {
        crotchet =  Math.round((float) 60000 / (float) tempo);
    }

    /**
     * Get the current crotchet value.
     * Note; this is dependant on the current tempo value and cannot be set by itself. 
     * Always update the tempo to update the crotchet!
     * 
     * @return The current crotchet at the current tempo.
     */
    public Integer getCrotchet() {
        return crotchet;
    }

    /**
     * Get an immutable term dictionary object.
     *
     * @return The termDictionary object.
     */
    public Map<String, Term> viewTermDictionary() {
        return Collections.unmodifiableMap(termDictionary);
    }

    /**
     * Put an item into the term dictionary.
     *
     * @return True if the term was added. False if an existing term was updated.
     */
    public Boolean putTerm(String name, Term term) {
        Term wasReplaced = termDictionary.put(name, term);

        markDirty();

        if (wasReplaced == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove an item from the term dictionary.
     *
     * @param termName The term to be deleted from the dictionary.
     * @return True if the term existed and was deleted. False if the term didn't exist.
     */
    public Boolean removeTerm(String termName) {
        Term wasRemoved = termDictionary.remove(termName);

        if (wasRemoved == null) {
            return false;
        } else {
            markDirty();

            return true;
        }
    }

    /**
     * Retrieve an item from the term dictionary.
     *
     * @param name The term we want out of the terms dictionary.
     * @return The term object if one existed at the name. Null if no term matched that name.
     */
    public Term getTerm(String name) {
        Term retrievedTerm = termDictionary.get(name);

        if (retrievedTerm == null) {
            return null;
        } else {
            return retrievedTerm;
        }
    }

    /**
     * Reset the term dictionary to a new state.
     */
    public void resetTerms() {
        termDictionary.clear();
    }

    /**
     * Used to get the first swing split value.
     *
     * @return The first swing split value.
     */
    public Float getFirstSwingSplit() {
        swingFirstSplit = ((float) 2 / (float) 3) * crotchet;
        return swingFirstSplit;
    }

    /**
     * Used to get the second swing split value.
     *
     * @return The second swing split value.
     */
    public Float getSecondSwingSplit() {
        swingSecondSplit = ((float) 1 / (float) 3) * crotchet;
        return swingSecondSplit;
    }

    /**
     * Open's a given project directory and loads the Xinity files from within it.
     *
     * @param projectLocation The directory of the project we're attempting to load.
     */
    public void openProject(File projectLocation) {
        createNewProject();

        projectName = projectLocation.getName();
        fileLocation = projectLocation;

        isNew = false;

        XmlConverter xmlConverter = new XmlConverter();

        String errorHeading = "";
        String errorContext = "";

        Boolean hasException = false;

        // Attempt to load the project meta information.
        try {
            File metaFile = new File(projectLocation + "/meta.xml");
            xmlConverter.loadAppVariables(metaFile);
        } catch (Exception e) {
            e.printStackTrace();

            hasException = true;

            errorHeading = "Failed to load project's 'Meta' file!";
            errorContext = "Could not load the project's 'meta.xml' file at location:\n"
                    + projectLocation.getAbsolutePath();
        }

        // Try load the terms
        try {
            File termsFile = new File(projectLocation + "/terms/terms.xml");
            xmlConverter.loadTermDataFromFile(termsFile);
        } catch (Exception e) {
            e.printStackTrace();

            if (!hasException) {
                hasException = true;

                errorHeading = "Failed to load project's 'Terms' file!";
                errorContext = "Could not load the project's 'terms.xml' file at location:\n"
                        + projectLocation.getAbsolutePath();
            } else {
                errorHeading = "Failed to load any project data!";
                errorContext = "No project files could not be found.";
            }
        }

        // If we have an exception throw this error modal.
        if (hasException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");

            alert.setHeaderText(errorHeading);
            alert.setContentText(errorContext);

            alert.showAndWait();
        }

        isDirty = false;
    }

    /**
     * Save the project using the last saved to file location. If somehow we don't have a
     * fileLocation to save to, that means we've somehow by-passed 'Save Project As' - so throw a
     * lovely Exception for whomever went and coded that.
     */
    public void saveProject() throws Exception {
        if (fileLocation != null) {
            doSave(fileLocation);
        } else {
            throw new Exception("File location for this project has not been set!");
        }
    }

    /**
     * Save the Project to a given file location.
     *
     * @param saveLocation File location we want to save to.
     */
    public void saveProjectAs(File saveLocation) {
        doSave(saveLocation);
    }

    /**
     * Saving method for the project.
     *
     * @param saveLocation The location to save the project in.
     */
    private void doSave(File saveLocation) {
        XmlConverter xmlConverter = new XmlConverter();

        // Save system variables
        Boolean success = xmlConverter.saveAppVariables(saveLocation);

        // Save term data
        if (success) {
            xmlConverter.saveTermDataToFile(saveLocation);
        }

        // Update project class
        projectName = saveLocation.getName();
        fileLocation = saveLocation;

        isNew = false;
        isDirty = false;
    }
}