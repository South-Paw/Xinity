package seng302.controllers.dslreference;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import org.apache.commons.lang3.StringUtils;

import seng302.util.enumerator.CommandCategory;
import seng302.util.map.CommandManualMap;
import seng302.util.object.CommandManual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the DSL Reference Card Scene.
 *
 * @author adg62
 */
public class DslReferenceSceneController {

    private static DslReferenceSceneController dslReferenceSceneController;

    // An integer to represent how we are sorting. 0 = by cat, 1 = by name.
    private Integer currentSortType;

    @FXML private ScrollPane commandListPane;
    @FXML private TextField commandSearchField;
    @FXML private ChoiceBox sortByChoiceBox;
    @FXML private ScrollPane manualPane;

    /** Constructor for the class. */
    public DslReferenceSceneController() {
        dslReferenceSceneController = this;
    }

    /**
     * Static method to get the class instance.
     *
     * @return This class instance.
     */
    public static DslReferenceSceneController getInstance() {
        return dslReferenceSceneController;
    }

    /**
     * The initialize function called when the class is finished being constructed.
     */
    @FXML
    public void initialize() {
        sortByChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
            new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue,
                                    Number number, Number number2) {
                    loadDslReference(observableValue.getValue().intValue());
                }
            }
        );

        commandSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchFieldChanged();
        });
    }

    /**
     * Method for loading the reference sheet.
     *
     * @param sortType An integer representing how we are sorting. 0 = by cat, 1 = by name.
     */
    public void loadDslReference(Integer sortType) {
        if (sortType == 1) {
            loadDslReferenceByName();
        } else {
            loadDslReferenceByCategory();
        }
    }

    /**
     * Loads the reference sheet in name view.
     */
    private void loadDslReferenceByName() {
        commandSearchField.setText("");
        currentSortType = 1;

        VBox commandItems = new VBox();

        for (CommandManual manual : CommandManualMap.getManualsMapSortedAlphabetically()) {
            commandItems.getChildren().add(createCommandItem(manual.getName()));
        }

        commandListPane.setContent(commandItems);
    }

    /**
     * Loads the reference sheet in category view.
     */
    private void loadDslReferenceByCategory() {
        commandSearchField.setText("");
        currentSortType = 0;

        VBox categoriesBox = new VBox();

        for (Map.Entry<CommandCategory, List<CommandManual>> entry :
                CommandManualMap.getCategoryToManualsMap().entrySet()) {

            // Get this categories name.
            String thisCategoriesName = entry.getKey().toString().toLowerCase();

            // Create a menu item for it.
            TitledPane thisCategory = new TitledPane();

            // Set the title to the categories name.
            thisCategory.setText(StringUtils.capitalize(thisCategoriesName));

            VBox categoryCommandItems = new VBox();

            // For each of this categories items.
            for (CommandManual manual : entry.getValue()) {
                categoryCommandItems.getChildren().add(createCommandItem(manual.getName()));
            }

            thisCategory.setContent(categoryCommandItems);

            categoriesBox.getChildren().add(thisCategory);
        }

        commandListPane.setContent(categoriesBox);
    }

    /**
     * Used to create a new command item.
     *
     * @param name The command item.
     * @return The item name.
     */
    private Pane createCommandItem(String name) {
        try {
            // Loads the item fxml
            Pane newPane = FXMLLoader.load(getClass().getResource(
                    "/scenes/dslreference/DslCommandItem.fxml"));

            DslCommandItemController.getInstance().setItemText(name);

            // Adds the command item to the scroll pane.
            return newPane;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Used to change the command item menu when the search field has changed.
     */
    private void searchFieldChanged() {
        String currentText = commandSearchField.getText().toLowerCase();

        Map<String, CommandManual> namesToManualsMap = new HashMap<>();

        for (CommandManual manual : CommandManualMap.getManualsMapSortedAlphabetically()) {
            namesToManualsMap.put(manual.getName(), manual);
        }

        if (currentText.length() > 0) {
            VBox commandItems = new VBox();

            for (String name : namesToManualsMap.keySet()) {
                // Adds the command if it starts with the search term
                if (name.toLowerCase().contains(currentText)) {
                    commandItems.getChildren().add(createCommandItem(name));
                }
            }

            // Checks if any commands where found
            if (commandItems.getChildren().size() == 0) {
                commandItems.getChildren().add(new Text("No matching commands!"));
            }

            commandListPane.setContent(commandItems);
        } else {
            loadDslReference(currentSortType);
        }
    }

    /**
     * Used to set the command item fields on the scene.
     *
     * @param name the command name.
     */
    public void setCommandItem(String name) {
        manualPane.setVisible(true);

        CommandManual thisItemManual = null;

        for (CommandManual manual : CommandManualMap.getManualsMapSortedAlphabetically()) {
            if (name.equals(manual.getName())) {
                thisItemManual = manual;
            }
        }

        if (thisItemManual != null) {
            try {
                // Loads the item fxml
                Pane newPane = FXMLLoader.load(getClass().getResource(
                        "/scenes/dslreference/DslManualItem.fxml"));

                DslManualItemController.getInstance().setUp(manualPane.getWidth(), thisItemManual);

                manualPane.setContent(newPane);
            } catch (Exception e) {
                e.printStackTrace();

                manualPane.setContent(new Text("Failed to load manual item!"));
            }
        } else {
            System.out.println("FAILED TO LOAD COMMAND MANUAL FOR: " + thisItemManual);
        }
    }
}
