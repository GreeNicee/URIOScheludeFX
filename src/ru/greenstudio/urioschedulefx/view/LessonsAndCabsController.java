package ru.greenstudio.urioschedulefx.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.model.Day;
import ru.greenstudio.urioschedulefx.model.Lecture;
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
        String selectedCab = cabListView.getSelectionModel().getSelectedItem();
        if (selectedCab != null) {
            if (isTextFieldOk(textCab)) {
                if (!alreadyInStringData(mainApp.getCabsListData(), textCab.getText(), mainApp.getPrimaryStage(), "аудиторию")) {
                    String cab = textCab.getText();
                    mainApp.getCabsListData().set(cabListView.getSelectionModel().getSelectedIndex(), cab);
                    cabListView.getSelectionModel().clearSelection();
                    textCab.setText("");
                    textCab.requestFocus();

                    for (Day day : mainApp.getSchedule().getDays()) {
                        for (Lecture lecture : day.getLectures()) {
                            if (lecture.getCab().equals(selectedCab))
                                lecture.setCab(cab);
                        }
                    }
                }
            } else showWarningOperation(mainApp.getPrimaryStage(), "изменить", "аудиторию");
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "аудиторию");
        }
    }

    @FXML
    private void handleDeleteCab() {
        int selectedIndex = cabListView.getSelectionModel().getSelectedIndex();
        String selectedCab = cabListView.getSelectionModel().getSelectedItem();
        if (selectedIndex >= 0) {
            cabListView.getItems().remove(selectedIndex);
            cabListView.getSelectionModel().clearSelection();
            textCab.setText("");
            textCab.requestFocus();

            for (Day day : mainApp.getSchedule().getDays()) {
                for (Lecture lecture : day.getLectures()) {
                    if (lecture.getCab().equals(selectedCab))
                        lecture.setCab("");
                }
            }
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

            for (int i = 0; i < mainApp.getGroupsData().size(); i++) {
                mainApp.getGroupsData().get(i).getLessonsHours().add(0);
                mainApp.getSchedule().getActualGroups().get(i).getLessonsHours().add(0);
            }
            mainApp.getLessonTableView().getItems().add(new Lesson(lesson, 0));
        }
    }

    @FXML
    private void handleEditLesson() {
        String selectedLesson = lessonListView.getSelectionModel().getSelectedItem();
        if (selectedLesson != null) {
            if (isTextFieldOk(textLesson)) {
                if (!alreadyInStringData(mainApp.getLessonsListData(), textLesson.getText(), mainApp.getPrimaryStage(), "предмет")) {
                    String lesson = textLesson.getText();
                    String oldLesson = lessonListView.getSelectionModel().getSelectedItem();
                    mainApp.getLessonsListData().set(lessonListView.getSelectionModel().getSelectedIndex(), lesson);
                    mainApp.getLessonTableView().getItems().set(lessonListView.getSelectionModel().getSelectedIndex(),
                            new Lesson(lesson, mainApp.getLessonTableView().getItems().
                                    get(lessonListView.getSelectionModel().getSelectedIndex()).getHours()));
                    lessonListView.getSelectionModel().clearSelection();
                    textLesson.setText("");
                    textLesson.requestFocus();

                    for (int i = 0; i < mainApp.getTeachersData().size(); i++) {
                        for (int j = 0; j < mainApp.getTeachersData().get(i).getLessons().size(); j++) {
                            if (mainApp.getTeachersData().get(i).getLessons().get(j).getName().equals(oldLesson)) {
                                mainApp.getTeachersData().get(i).getLessons().get(j).setName(lesson);
                                mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).setName(lesson);
                                break;
                            }
                        }
                    }

                    for (int i = 0; i < mainApp.getSchedule().getDays().size(); i++) {
                        for (int j = 0; j < mainApp.getSchedule().getDays().get(i).getLectures().size(); j++) {
                            if (mainApp.getSchedule().getDays().get(i).getLectures().get(j).getLesson().equals(oldLesson))
                                mainApp.getSchedule().getDays().get(i).getLectures().get(j).setLesson(lesson);
                        }
                    }
                }
            } else showWarningOperation(mainApp.getPrimaryStage(), "изменить", "предмет");
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "предмет");
        }
    }

    @FXML
    private void handleDeleteLesson() {
        int selectedIndex = lessonListView.getSelectionModel().getSelectedIndex();
        String selectedItem = lessonListView.getSelectionModel().getSelectedItem();
        if (selectedIndex >= 0) {
            for (int i = 0; i < mainApp.getGroupsData().size(); i++) {
                for (int j = 0; j < mainApp.getLessonsListData().size(); j++) {
                    if (mainApp.getLessonsListData().get(j).equals(selectedItem)) {
                        mainApp.getGroupsData().get(i).getLessonsHours().remove(j);
                        mainApp.getSchedule().getActualGroups().get(i).getLessonsHours().remove(j);
                        break;
                    }
                }
            }

            for (int i = 0; i < mainApp.getTeachersData().size(); i++) {
                for (int j = 0; j < mainApp.getTeachersData().get(i).getLessons().size(); j++) {
                    if (mainApp.getTeachersData().get(i).getLessons().get(j).getName().equals(selectedItem)) {
                        mainApp.getTeachersData().get(i).getLessons().remove(j);
                        mainApp.getSchedule().getActualTeachers().get(i).getLessons().remove(j);
                        break;
                    }
                }
            }

            for (int i = 0; i < mainApp.getSchedule().getDays().size(); i++) {
                for (int j = 0; j < mainApp.getSchedule().getDays().get(i).getLectures().size(); j++) {
                    if (mainApp.getSchedule().getDays().get(i).getLectures().get(j).getLesson().equals(selectedItem)) {
                        mainApp.getSchedule().getDays().get(i).getLectures().get(j).setLesson("");
                        mainApp.getSchedule().getDays().get(i).getLectures().get(j).setTeacher("");
                    }
                }
            }

            lessonListView.getItems().remove(selectedIndex);
//            mainApp.getLessonTableView().getItems().remove(selectedIndex);
            for (int i = 0; i < mainApp.getTeacherLessonTableView().getItems().size(); i++) {
                if (mainApp.getTeacherLessonTableView().getItems().get(i).getName().equals(selectedItem)) {
                    mainApp.getTeacherLessonTableView().getItems().remove(i);
                    break;
                }
            }
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
