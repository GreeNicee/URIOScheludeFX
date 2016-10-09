package ru.greenstudio.urioschedulefx.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ru.greenstudio.urioschedulefx.MainApp;

import static ru.greenstudio.urioschedulefx.Utils.Alerts.alreadyInStringData;
import static ru.greenstudio.urioschedulefx.Utils.Alerts.showWarningOperation;
import static ru.greenstudio.urioschedulefx.Utils.IsInputOk.isTextFieldOk;

public class LessonsAndCabsController {
    @FXML
    private ListView<String> lessonListView;
    @FXML
    private ListView<String> cabListView;
    @FXML
    private TextField textLesson;
    @FXML
    private TextField textCab;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        lessonListView.setItems(mainApp.getLessonsListData());
        cabListView.setItems(mainApp.getCabsListData());
    }

    public LessonsAndCabsController() {
    }

    @FXML
    private void handleDeleteLesson() {
        int selectedIndex = lessonListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            lessonListView.getItems().remove(selectedIndex);
            lessonListView.getSelectionModel().clearSelection();
            textLesson.setText("");
            textLesson.requestFocus();
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "удалить", "предмет");
        }
    }

    @FXML
    private void handleAddCab() {
        if (!isTextFieldOk(textCab)) {
            showWarningOperation(mainApp.getPrimaryStage(), "добавить", "аудиторию");
            return;
        }
        if (!alreadyInStringData(mainApp.getCabsListData(), textCab.getText(), mainApp.getPrimaryStage(), "аудиторию")) {
            String cab = textCab.getText();
            mainApp.getCabsListData().add(cab);
            cabListView.getSelectionModel().clearSelection();
            textCab.setText("");
            textCab.requestFocus();
        }
    }

    @FXML
    private void handleEditCab() {
        String selectedLesson = cabListView.getSelectionModel().getSelectedItem();
        if (selectedLesson != null) {
            if (isTextFieldOk(textCab)) {
                if (!alreadyInStringData(mainApp.getCabsListData(), textCab.getText(), mainApp.getPrimaryStage(), "аудиторию")) {
                    String cab = textCab.getText();
                    mainApp.getCabsListData().set(cabListView.getSelectionModel().getSelectedIndex(), cab);
                    cabListView.getSelectionModel().clearSelection();
                    textCab.setText("");
                    textCab.requestFocus();
                }
            } else showWarningOperation(mainApp.getPrimaryStage(), "изменить", "аудиторию");
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "аудиторию");
        }
    }

    @FXML
    private void handleClickListViewCabs() {
        String selectedCab = cabListView.getSelectionModel().getSelectedItem();
        if (selectedCab != null) {
            textCab.setText(selectedCab);
        }
    }

    @FXML
    private void handleDeleteCab() {
        int selectedIndex = cabListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            cabListView.getItems().remove(selectedIndex);
            cabListView.getSelectionModel().clearSelection();
            textCab.setText("");
            textCab.requestFocus();
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "удалить", "аудиторию");
        }
    }

    @FXML
    private void handleAddLesson() {
        if (!isTextFieldOk(textLesson)) {
            showWarningOperation(mainApp.getPrimaryStage(), "добавить", "предмет");
            return;
        }
        if (!alreadyInStringData(mainApp.getLessonsListData(), textLesson.getText(), mainApp.getPrimaryStage(), "предмет")) {
            String lesson = textLesson.getText();
            mainApp.getLessonsListData().add(lesson);
            lessonListView.getSelectionModel().clearSelection();
            textLesson.setText("");
            textLesson.requestFocus();
        }
    }

    @FXML
    private void handleEditLesson() {
        String selectedLesson = lessonListView.getSelectionModel().getSelectedItem();
        if (selectedLesson != null) {
            if (isTextFieldOk(textLesson)) {
                if (!alreadyInStringData(mainApp.getLessonsListData(), textLesson.getText(), mainApp.getPrimaryStage(), "предмет")) {
                    String lesson = textLesson.getText();
                    mainApp.getLessonsListData().set(lessonListView.getSelectionModel().getSelectedIndex(), lesson);
                    lessonListView.getSelectionModel().clearSelection();
                    textLesson.setText("");
                    textLesson.requestFocus();
                }
            } else showWarningOperation(mainApp.getPrimaryStage(), "изменить", "предмет");
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "предмет");
        }
    }

    @FXML
    private void handleClickListViewLessons() {
        String selectedLesson = lessonListView.getSelectionModel().getSelectedItem();
        if (selectedLesson != null) {
            textLesson.setText(selectedLesson);
        }
    }
}
