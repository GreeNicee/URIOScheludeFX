package ru.greenstudio.urioschedulefx.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.model.Group;
import ru.greenstudio.urioschedulefx.model.Teacher;

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

        textLesson.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                handleAddLesson();
            }
        });

        textCab.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                handleAddCab();
            }
        });
    }

    public LessonsAndCabsController() {
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
    private void handleClickListViewCabs() {
        String selectedCab = cabListView.getSelectionModel().getSelectedItem();
        if (selectedCab != null) {
            textCab.setText(selectedCab);
            textCab.requestFocus();
        }
    }

    @FXML//TODO Обновление в группах
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

            ObservableList<Group> groups = mainApp.getGroupsData();
            for (Group group : groups) {
                group.getLessonsHours().add(0);
            }
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

    @FXML//TODO Обновление в группах
    private void handleDeleteLesson() {
        int selectedIndex = lessonListView.getSelectionModel().getSelectedIndex();
        String selectedItem = lessonListView.getSelectionModel().getSelectedItem();
        if (selectedIndex >= 0) {
            ObservableList<Group> groups = mainApp.getGroupsData();
            for (Group group : groups) {
                for (int j = 0; j < mainApp.getLessonsListData().size(); j++) {
                    if (mainApp.getLessonsListData().get(j).equals(selectedItem)) {
                        group.getLessonsHours().remove(j);
                        break;
                    }
                }
            }

            ObservableList<Teacher> teachers = mainApp.getTeachersData();
            for (Teacher teacher : teachers) {
                for (int j = 0; j < teacher.getLessons().size(); j++) {
                    if (teacher.getLessons().get(j).getName().equals(selectedItem)) {
                        teacher.getLessons().remove(j);
                        break;
                    }
                }
            }

            for (int i = 0; i < mainApp.getMaxLessonsData().size(); i++) {
                if (mainApp.getMaxLessonsData().get(i).getName().equals(selectedItem)) {
                    mainApp.getLessonsData().remove(i);
                    mainApp.getMaxLessonsData().remove(i);
                    break;
                }
            }

            lessonListView.getItems().remove(selectedIndex);
            lessonListView.getSelectionModel().clearSelection();
            textLesson.setText("");
            textLesson.requestFocus();

        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "удалить", "предмет");
        }
    }

    @FXML
    private void handleClickListViewLessons() {
        String selectedLesson = lessonListView.getSelectionModel().getSelectedItem();
        if (selectedLesson != null) {
            textLesson.setText(selectedLesson);
            textLesson.requestFocus();
        }
    }
}
