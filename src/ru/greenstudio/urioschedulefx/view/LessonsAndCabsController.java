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
    private ListView<Cab> cabListView;
    @FXML
    private TextField textLesson;
    @FXML
    private TextField textCab;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        lessonListView.setItems(mainApp.getLessonData());
    }

    public LessonsAndCabsController() {
    }

    @FXML
    private void handleDeleteLesson() {
        int selectedIndex = lessonListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            lessonListView.getItems().remove(selectedIndex);
        } else {
            // Ничего не выбрано.
            showWarningOperation("удал", mainApp.getPrimaryStage(), "предмет");

        }
    }

    @FXML
    private void handleAddLesson() {
        if (!isTextFieldOk(textLesson)) {
            showWarningOperation("добав", mainApp.getPrimaryStage(), "предмет");
            return;
        }
        if (!alreadyInStringData(mainApp.getLessonData(), textLesson.getText(), mainApp.getPrimaryStage())) {
            Lesson lesson = new Lesson(textLesson.getText());
            mainApp.getLessonData().add(lesson.getName());
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопка Edit...
     * Открывает диалоговое окно для изменения выбранного адресата.
     */
    @FXML
    private void handleEditPerson() {
        String selectedLesson = lessonListView.getSelectionModel().getSelectedItem();
        if ((selectedLesson != null) || (!isTextFieldOk(textLesson))) {
            if (!alreadyInStringData(mainApp.getLessonData(), textLesson.getText(), mainApp.getPrimaryStage())) {
                Lesson lesson = new Lesson(textLesson.getText());
                mainApp.getLessonData().set(lessonListView.getSelectionModel().getSelectedIndex(), lesson.getName());
            }
        } else {
            // Ничего не выбрано.
            showWarningOperation("измен", mainApp.getPrimaryStage(), "предмет");
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
