package seng302.workspace;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class that handles the test records for all tutors.
 *
 * @author avh17.
 */
public class TutorRecords {

    /** tutor records instance. */
    private static TutorRecords tutorRecords;

    private String filePath = System.getProperty("user.home");
    private Boolean hasSavedInSession = false;
    private String fileName;
    private FileWriter fileWriter;

    /**
     * Private constructor.
     */
    private TutorRecords() {
        tutorRecords = this;
    }

    /**
     * Static function used to get the instance of the TutorRecords class.
     */
    public static TutorRecords getInstance() {
        if (tutorRecords == null) {
            tutorRecords = new TutorRecords();
        }
        return tutorRecords;
    }

    /**
     * This function handles the dialog for saving a test record.
     *
     * @param tutorName The name of the tutor.
     * @param testRecords The test records.
     */
    public void testRecordSaving(String tutorName, ArrayList<List<String>> testRecords) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Tutor Record");

        if (!hasSavedInSession) {
            alert.setHeaderText("Would you like to save a record of this test?");

            ButtonType saveRecordButton = new ButtonType("Save");
            ButtonType discardRecordButton = new ButtonType("Discard");

            alert.getButtonTypes().setAll(saveRecordButton, discardRecordButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == saveRecordButton) {
                //Save the tutor records
                saveTutorRecord(tutorName, testRecords);
            }
        } else {
            alert.setHeaderText("A previous tutor record has been saved in this session!");
            alert.setContentText("Would you like to save this record to the same file?");

            ButtonType saveButton = new ButtonType("Save");
            ButtonType saveNewButton = new ButtonType("Save New");
            ButtonType doNotSaveButton = new ButtonType("Discard");

            alert.getButtonTypes().setAll(saveButton, saveNewButton, doNotSaveButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == saveButton) {
                //save to same file
                saveRecordToSameFile(tutorName, testRecords);
            } else if (result.get() == saveNewButton) {
                //Save the tutor records to a new file
                saveTutorRecord(tutorName, testRecords);
            }
        }
    }

    /**
     * Function that handles tutor record saving.
     *
     * @param tutorName The name of the tutor.
     * @param testRecords The records from the tutor test.
     */
    private void saveTutorRecord(String tutorName, ArrayList<List<String>> testRecords) {
        String defaultFileName = generateDefaultFileName(tutorName);

        File directory = new File(filePath);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(defaultFileName);
        fileChooser.setInitialDirectory(directory);
        fileChooser.setTitle("Open Resource File");
        File tutorFile = fileChooser.showSaveDialog(new Stage());

        //Update the file path to the last saved directory
        try {
            filePath = tutorFile.getPath().substring(0,
                    (tutorFile.getPath().length() - tutorFile.getName().length() - 1));
            fileWriter = new FileWriter(tutorFile);
            saveRecordToFile(tutorName, testRecords);
            fileName = tutorFile.getName();
            hasSavedInSession = true;
        } catch (Exception ex) {
            System.out.println("User cancelled action");
        }
    }

    /**
     * Function that saves data to an existing tutor file.
     *
     * @param tutorName The tutor name.
     * @param testRecords The tutor records.
     */
    private void saveRecordToSameFile(String tutorName, ArrayList<List<String>> testRecords) {
        try {
            fileWriter = new FileWriter(new File(filePath + File.separator + fileName), true);
            saveRecordToFile(tutorName, testRecords);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Used to output the records to a txt file.
     *
     * @param tutorName The tutor name.
     * @param testRecords The test records to output.
     */
    private void saveRecordToFile(String tutorName, ArrayList<List<String>> testRecords) {
        Integer correctAnswers = 0;
        Integer incorrectAnswers = 0;
        Integer numberOfQuestions = 0;
        LocalDateTime currentDate = LocalDateTime.now();


        // Attempt to write file
        try {
            fileWriter.write("Tutor Name: " + tutorName + "\n");
            fileWriter.write("Date: " + currentDate.getDayOfMonth() + "/"
                    + currentDate.getMonthValue() + "/"
                    + currentDate.getYear() + "\n\n" + "Questions\n\n");

            //Output questions
            for (List<String> questionRecord: testRecords) {
                fileWriter.write("Question: " + questionRecord.get(0) + "\n");
                fileWriter.write("Answer Given: " + questionRecord.get(1) + "\n");
                fileWriter.write("Answer Was: " + questionRecord.get(2) + "\n\n");

                if (questionRecord.get(2).equals("Correct")) {
                    correctAnswers++;
                } else {
                    incorrectAnswers++;
                }
                numberOfQuestions++;
            }

            //Output summary
            fileWriter.write("Summary\n\n");
            fileWriter.write("Number of questions: " + numberOfQuestions + "\n");
            fileWriter.write("Questions answered correctly: " + correctAnswers + "\n");
            fileWriter.write("Questions answered incorrectly: " + incorrectAnswers + "\n");

            //Calculate score percentage
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            Double correctTests = (double) correctAnswers;
            Double totalTests = (double) numberOfQuestions;
            Double scorePercentage = (correctTests / totalTests) * 100.0;

            if (numberOfQuestions > 0) {
                fileWriter.write("Test Percentage: " + df.format(scorePercentage) + "%\n\n");
            } else {
                fileWriter.write("Test Percentage: 0%\n\n");
            }

            fileWriter.write("---------------------------------------------------------------------"
                    + "---\n\n");

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to generate a default nem for the tutor record file.
     *
     * @param tutorName The name of the tutor.
     * @return The file name.
     */
    private String generateDefaultFileName(String tutorName) {
        LocalDateTime currentDate = LocalDateTime.now();
        String fileName = "";

        fileName += tutorName + " ";
        fileName += currentDate.getDayOfMonth() + "-";
        fileName += currentDate.getMonthValue() + "-";
        fileName += currentDate.getYear() + " ";
        fileName += System.currentTimeMillis();

        return fileName;
    }

}
