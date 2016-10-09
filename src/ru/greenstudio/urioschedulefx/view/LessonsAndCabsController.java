package ru.greenstudio.urioschedulefx.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.model.Cab;
import ru.greenstudio.urioschedulefx.model.Lesson;

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
        lessonListView.setItems(mainApp.getLessonsData());
        cabListView.setItems(mainApp.getCabsData());
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
            showWarningOperation(mainApp.getPrimaryStage(), "удалить","предмет");
        }
    }

    @FXML
    private void handleAddCab() {
        if (!isTextFieldOk(textCab)) {
            showWarningOperation(mainApp.getPrimaryStage(), "добавить","аудиторию");
            return;
        }
        if (!alreadyInStringData(mainApp.getCabsData(), textCab.getText(), mainApp.getPrimaryStage(),"аудиторию")) {
            Lesson lesson = new Lesson(textCab.getText());
            mainApp.getCabsData().add(lesson.getName());
            cabListView.getSelectionModel().clearSelection();
            textCab.setText("");
            textCab.requestFocus();
        }
    }

    @FXML
    private void handleEditCab() {
        String selectedLesson = cabListView.getSelectionModel().getSelectedItem();
        if (selectedLesson != null) {
            if (isTextFieldOk(textCab))   {
                if (!alreadyInStringData(mainApp.getCabsData(), textCab.getText(), mainApp.getPrimaryStage(),"аудиторию")) {
                    Lesson lesson = new Lesson(textCab.getText());
                    mainApp.getCabsData().set(cabListView.getSelectionModel().getSelectedIndex(), lesson.getName());
                    cabListView.getSelectionModel().clearSelection();
                    textCab.setText("");
                    textCab.requestFocus();
                }
            }else showWarningOperation(mainApp.getPrimaryStage(),"изменить","аудиторию");
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить","аудиторию");
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
            showWarningOperation(mainApp.getPrimaryStage(), "удалить","аудиторию");
        }
    }

    @FXML
    private void handleAddLesson() {
        if (!isTextFieldOk(textLesson)) {
            showWarningOperation(mainApp.getPrimaryStage(), "добавить","предмет");
            return;
        }
        if (!alreadyInStringData(mainApp.getLessonsData(), textLesson.getText(), mainApp.getPrimaryStage(),"предмет")) {
            Lesson lesson = new Lesson(textLesson.getText());
            mainApp.getLessonsData().add(lesson.getName());
            lessonListView.getSelectionModel().clearSelection();
            textLesson.setText("");
            textLesson.requestFocus();
        }
    }

    @FXML
    private void handleEditLesson() {
        String selectedLesson = lessonListView.getSelectionModel().getSelectedItem();
        if (selectedLesson != null) {
            if (isTextFieldOk(textLesson))   {
                if (!alreadyInStringData(mainApp.getLessonsData(), textLesson.getText(), mainApp.getPrimaryStage(),"предмет")) {
                    Lesson lesson = new Lesson(textLesson.getText());
                    mainApp.getLessonsData().set(lessonListView.getSelectionModel().getSelectedIndex(), lesson.getName());
                    lessonListView.getSelectionModel().clearSelection();
                    textLesson.setText("");
                    textLesson.requestFocus();
                }
            }else showWarningOperation(mainApp.getPrimaryStage(),"изменить","предмет");
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить","предмет");
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
