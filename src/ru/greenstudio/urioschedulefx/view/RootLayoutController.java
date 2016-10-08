package ru.greenstudio.urioschedulefx.view;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.greenstudio.urioschedulefx.MainApp;

public class RootLayoutController {
    private MainApp mainApp;

    @FXML
    private TabPane tabPane ;
    @FXML
    private Tab tabSchedule;
    @FXML
    private Tab tabTeachers;
    @FXML
    private Tab tabGroups;
    @FXML
    private Tab tabLessonsAndCabs;

    public Tab getTabLessonsAndCabs() {
        return tabLessonsAndCabs;
    }

    @FXML

    private Tab tabTimeSchedule;

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
//        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
//        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
//        personTable.setItems(mainApp.getPersonData());
    }

    public RootLayoutController() {
    }
}
