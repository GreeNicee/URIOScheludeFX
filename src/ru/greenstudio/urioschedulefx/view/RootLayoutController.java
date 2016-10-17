package ru.greenstudio.urioschedulefx.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import ru.greenstudio.urioschedulefx.MainApp;

import java.io.IOException;

public class RootLayoutController {
    private MainApp mainApp;

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabSchedule;
    @FXML
    private Tab tabTeachers;
    @FXML
    private Tab tabGroups;
    @FXML
    private Tab tabLessonsAndCabs;

    @FXML
    private void initialize() {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(MainApp.class.getResource("view/LessonsAndCabsLayout.fxml"));
//        SplitPane lessonsAndCabs = null;
//        try {
//            lessonsAndCabs = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        AnchorPane anchorPane = (AnchorPane) tabLessonsAndCabs.getContent();
//        anchorPane.getChildren().add(lessonsAndCabs);
//
//        AnchorPane.setTopAnchor(lessonsAndCabs, 0.0);
//        AnchorPane.setBottomAnchor(lessonsAndCabs, 0.0);
//        AnchorPane.setLeftAnchor(lessonsAndCabs, 0.0);
//        AnchorPane.setRightAnchor(lessonsAndCabs, 0.0);
//
//        loader = new FXMLLoader();
//        loader.setLocation(MainApp.class.getResource("view/GroupsLayout.fxml"));
//        SplitPane groups = null;
//        try {
//            groups = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        anchorPane = (AnchorPane) tabGroups.getContent();
//        anchorPane.getChildren().add(groups);
//
//        AnchorPane.setTopAnchor(groups, 0.0);
//        AnchorPane.setBottomAnchor(groups, 0.0);
//        AnchorPane.setLeftAnchor(groups, 0.0);
//        AnchorPane.setRightAnchor(groups, 0.0);
//
//        loader = new FXMLLoader();
//        loader.setLocation(MainApp.class.getResource("view/TeachersLayout.fxml"));
//        SplitPane teachers = null;
//        try {
//            teachers = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        anchorPane = (AnchorPane) tabTeachers.getContent();
//        anchorPane.getChildren().add(teachers);
//
//        AnchorPane.setTopAnchor(teachers, 0.0);
//        AnchorPane.setBottomAnchor(teachers, 0.0);
//        AnchorPane.setLeftAnchor(teachers, 0.0);
//        AnchorPane.setRightAnchor(teachers, 0.0);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public RootLayoutController() {
    }
}
