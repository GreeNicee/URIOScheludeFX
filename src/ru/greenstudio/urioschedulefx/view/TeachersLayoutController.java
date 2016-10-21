package ru.greenstudio.urioschedulefx.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.Utils.IsInputOk;
import ru.greenstudio.urioschedulefx.model.Lesson;
import ru.greenstudio.urioschedulefx.model.Teacher;

import static ru.greenstudio.urioschedulefx.Utils.Alerts.alreadyInTeacherData;
import static ru.greenstudio.urioschedulefx.Utils.Alerts.showWarningOperation;
import static ru.greenstudio.urioschedulefx.Utils.Funcs.checkLessonsData;
import static ru.greenstudio.urioschedulefx.Utils.IsInputOk.isTextFieldOk;

public class TeachersLayoutController {
    @FXML
    private ListView<Teacher> teachersListView;
    @FXML
    private TableView<Lesson> lessonTableView;
    @FXML
    private TextField textTeacher;
    @FXML
    private ComboBox comboBoxTeacher;
    @FXML
    private TableColumn<Lesson, String> nameLessonColumn;
    @FXML
    private TableColumn<Lesson, Integer> hoursLessonColumn;
    @FXML
    private TextField textLessonHours;
    @FXML
    private Label labelLessonHours;


    private MainApp mainApp;

    public TeachersLayoutController() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

//        new AutoCompleteComboboxListener(comboBoxTeacher);

//        ObservableList<String> teachersNames = FXCollections.observableArrayList();
//        for (int i = 0; i < mainApp.getTeachersData().size(); i++) {
//            teachersNames.add(mainApp.getTeachersData().get(i).getName());
//        }

        teachersListView.setItems(mainApp.getTeachersData());

        teachersListView.setCellFactory(new Callback<ListView<Teacher>, ListCell<Teacher>>() {
            @Override
            public ListCell<Teacher> call(ListView<Teacher> param) {
                return new ListCell<Teacher>() {
                    @Override
                    protected void updateItem(Teacher item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });

        nameLessonColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        hoursLessonColumn.setCellValueFactory(cellData -> cellData.getValue().getHoursProperty().asObject());
        hoursLessonColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        showTeacherDetails(null);
        teachersListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTeacherDetails(newValue)
        );

        comboBoxTeacher.setOnMouseClicked(event -> {
            comboBoxTeacher.getItems().clear();
            if (teachersListView.getSelectionModel().getSelectedItem() != null) {
                for (int i = 0; i < mainApp.getMaxLessonsData().size(); i++) {
                    System.out.println(mainApp.getMaxLessonsData().get(i));
                    System.out.println(mainApp.getMaxLessonsData().get(i).getHours() - mainApp.getLessonsData().get(i).getHours());
                    if (mainApp.getMaxLessonsData().get(i).getHours() - mainApp.getLessonsData().get(i).getHours() > 0)
                        comboBoxTeacher.getItems().add(mainApp.getMaxLessonsData().get(i).getName());
                }

                for (int i = 0; i < lessonTableView.getItems().size(); i++) {
                    for (int j = 0; j < comboBoxTeacher.getItems().size(); j++) {
                        if (lessonTableView.getItems().get(i).getName().equals(comboBoxTeacher.getItems().get(j).toString())) {
                            comboBoxTeacher.getItems().remove(j);
                            break;
                        }
                    }
                }
            }

            comboBoxTeacher.hide();
            comboBoxTeacher.setVisibleRowCount(100);
            comboBoxTeacher.show();
        });

        textTeacher.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                handleAddTeacher();
            }
        });

        textLessonHours.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                handleEditLesson();
            }
        });
    }

    @FXML
    private void handleAddLesson() {
        if (!isTextFieldOk(new TextField(comboBoxTeacher.getSelectionModel().getSelectedItem().toString()))) {
            showWarningOperation(mainApp.getPrimaryStage(), "добавить", "преподавателя");
            return;
        }
        String lessonName = comboBoxTeacher.getSelectionModel().getSelectedItem().toString();
        int index = teachersListView.getSelectionModel().getSelectedIndex();

        mainApp.getTeachersData().get(index).getLessons().add(new Lesson(lessonName, 0));
        lessonTableView.getItems().add(new Lesson(lessonName, 0));
        lessonTableView.getSelectionModel().clearSelection();
        comboBoxTeacher.getSelectionModel().clearSelection();
        comboBoxTeacher.requestFocus();
    }

    @FXML
    private void handleEditLesson() {//TODO Возможно нужно обнулять часы)
        if (!isTextFieldOk(new TextField(comboBoxTeacher.getSelectionModel().getSelectedItem().toString()))) {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "преподавателя");
            return;
        }
        String lessonName = comboBoxTeacher.getSelectionModel().getSelectedItem().toString();
        int index = teachersListView.getSelectionModel().getSelectedIndex();
        int lessonIndex = lessonTableView.getSelectionModel().getSelectedIndex();

        mainApp.getTeachersData().get(index).getLessons().get(lessonIndex).setName(lessonName);
        lessonTableView.getItems().get(lessonIndex).setName(lessonName);
        lessonTableView.getSelectionModel().clearSelection();
        comboBoxTeacher.getSelectionModel().clearSelection();
        comboBoxTeacher.requestFocus();
    }

    @FXML
    private void handleDeleteLesson() {
        int selectedIndex = lessonTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            for (int i = 0; i < mainApp.getLessonsData().size(); i++) {
                if (mainApp.getLessonsData().get(i).getName().equals(lessonTableView.getItems().get(selectedIndex).getName())) {
                    mainApp.getLessonsData().get(i).setHours(
                            mainApp.getLessonsData().get(i).getHours() - lessonTableView.getItems().get(selectedIndex).getHours());
                    break;
                }
            }
            int teacherIndex = teachersListView.getSelectionModel().getSelectedIndex();
            mainApp.getTeachersData().get(teacherIndex).getLessons().remove(selectedIndex);
            lessonTableView.getItems().remove(selectedIndex);
            lessonTableView.getSelectionModel().clearSelection();
            comboBoxTeacher.getSelectionModel().clearSelection();
            comboBoxTeacher.requestFocus();
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "удалить", "предмет");
        }
        System.out.println(mainApp.getMaxLessonsData());
        System.out.println(mainApp.getLessonsData());
    }

    @FXML
    private void handleMaxLessonHours() {
        Lesson selectedLesson = lessonTableView.getSelectionModel().getSelectedItem();

        if (selectedLesson != null) {
            Lesson oldLesson = new Lesson(selectedLesson.getName(), selectedLesson.getHours());
            int lessonHours = Integer.parseInt(labelLessonHours.getText().substring(5));
            selectedLesson.setHours(lessonHours);

            System.out.println(selectedLesson);
            mainApp.getTeachersData().get(teachersListView.getSelectionModel().getSelectedIndex()).
                    getLessons().get(lessonTableView.getSelectionModel().getSelectedIndex()).setHours(lessonHours);
            lessonTableView.getSelectionModel().clearSelection();
            textLessonHours.setText("");
            textLessonHours.requestFocus();

            Lesson newLesson = new Lesson(oldLesson.getName(), selectedLesson.getHours() - oldLesson.getHours());
            ObservableList<Lesson> lessonsData = mainApp.getLessonsData();
            checkLessonsData(selectedLesson, newLesson, lessonsData);

        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "предмет");
        }
    }

    @FXML
    private void handleHalfLessonHours() {
        Lesson selectedLesson = lessonTableView.getSelectionModel().getSelectedItem();

        if (selectedLesson != null) {
            Lesson oldLesson = new Lesson(selectedLesson.getName(), selectedLesson.getHours());
            int lessonHours = Integer.parseInt(labelLessonHours.getText().substring(5)) / 2;
            selectedLesson.setHours(lessonHours);

            System.out.println(selectedLesson);
            mainApp.getTeachersData().get(teachersListView.getSelectionModel().getSelectedIndex()).
                    getLessons().get(lessonTableView.getSelectionModel().getSelectedIndex()).setHours(lessonHours);
            lessonTableView.getSelectionModel().clearSelection();
            textLessonHours.setText("");
            textLessonHours.requestFocus();

            Lesson newLesson = new Lesson(oldLesson.getName(), selectedLesson.getHours() - oldLesson.getHours());
            ObservableList<Lesson> lessonsData = mainApp.getLessonsData();
            checkLessonsData(selectedLesson, newLesson, lessonsData);

        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "предмет");
        }
    }

    @FXML
    private void handleEditLessonHours() {
        Lesson selectedLesson = lessonTableView.getSelectionModel().getSelectedItem();

        if (selectedLesson != null) {
            if (isTextFieldOk(textLessonHours, IsInputOk.TextFieldType.NUMERIC)) {
                Lesson oldLesson = new Lesson(selectedLesson.getName(), selectedLesson.getHours());
                int lessonHours = Integer.parseInt(textLessonHours.getText());
                selectedLesson.setHours(lessonHours);
                System.out.println(selectedLesson);
                mainApp.getTeachersData().get(teachersListView.getSelectionModel().getSelectedIndex()).
                        getLessons().get(lessonTableView.getSelectionModel().getSelectedIndex()).setHours(selectedLesson.getHours());
                lessonTableView.getSelectionModel().clearSelection();
                textLessonHours.setText("");
                textLessonHours.requestFocus();

                Lesson newLesson = new Lesson(oldLesson.getName(), selectedLesson.getHours() - oldLesson.getHours());
                ObservableList<Lesson> lessonsData = mainApp.getLessonsData();
                checkLessonsData(selectedLesson, newLesson, lessonsData);

            } else showWarningOperation(mainApp.getPrimaryStage(), "изменить", "предмет");
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "предмет");
        }
    }

    @FXML
    private void handleDeleteLessonHours() {
        Lesson selectedLesson = lessonTableView.getSelectionModel().getSelectedItem();

        if (selectedLesson != null) {
            Lesson oldLesson = new Lesson(selectedLesson.getName(), selectedLesson.getHours());
            int lessonHours = 0;
            selectedLesson.setHours(lessonHours);
            mainApp.getGroupsData().get(teachersListView.getSelectionModel().getSelectedIndex()).
                    getLessonsHours().set(lessonTableView.getSelectionModel().getSelectedIndex(), selectedLesson.getHours());
            lessonTableView.getSelectionModel().clearSelection();
            textLessonHours.setText("");
            textLessonHours.requestFocus();

            Lesson newLesson = new Lesson(oldLesson.getName(), selectedLesson.getHours() - oldLesson.getHours());
            ObservableList<Lesson> lessonsData = mainApp.getLessonsData();
            checkLessonsData(selectedLesson, newLesson, lessonsData);
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "\"обнулить\"", "предмет");
        }
    }

    @FXML
    private void handleClickTableViewLessons() {
        Lesson selectedLesson = lessonTableView.getSelectionModel().getSelectedItem();
        if (selectedLesson != null) {
            textLessonHours.setText(String.valueOf(selectedLesson.getHours()));

            int index = -1;
            for (int i = 0; i < mainApp.getMaxLessonsData().size(); i++) {
                if (mainApp.getMaxLessonsData().get(i).getName().equals(selectedLesson.getName())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                int maxHours = mainApp.getMaxLessonsData().get(index).getHours() - mainApp.getLessonsData().get(index).getHours();
                maxHours += selectedLesson.getHours();
                labelLessonHours.setText("MAX: " + maxHours);
            }
            textLessonHours.requestFocus();
        }
    }

    private void showTeacherDetails(Teacher teacher) {
        if (teacher != null) {
            lessonTableView.getItems().clear();
            for (int i = 0; i < teacher.getLessons().size(); i++) {
                Lesson lesson = teacher.getLessons().get(i);
                lessonTableView.getItems().add(i, lesson);
            }
        } else {
            lessonTableView.getItems().clear();
        }
    }

    @FXML
    private void handleAddTeacher() {
        if (!isTextFieldOk(textTeacher)) {
            showWarningOperation(mainApp.getPrimaryStage(), "добавить", "преподавателя");
            return;
        }
        if (!alreadyInTeacherData(mainApp.getTeachersData(), textTeacher.getText(), mainApp.getPrimaryStage(), "преподаватель")) {
            String teacherName = textTeacher.getText();

            mainApp.getTeachersData().add(new Teacher(teacherName, FXCollections.observableArrayList()));
            teachersListView.getSelectionModel().clearSelection();
            textTeacher.setText("");
            textTeacher.requestFocus();
        }
    }

    @FXML
    private void handleEditTeacher() {
        Teacher selectedTeacher = teachersListView.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            if (isTextFieldOk(textTeacher)) {
                if (!alreadyInTeacherData(mainApp.getTeachersData(), textTeacher.getText(), mainApp.getPrimaryStage(), "преподаватель")) {
                    selectedTeacher.setName(textTeacher.getText());
                    mainApp.getTeachersData().set(teachersListView.getSelectionModel().getSelectedIndex(), selectedTeacher);
                    teachersListView.getSelectionModel().clearSelection();
                    textTeacher.setText("");
                    textTeacher.requestFocus();
                }
            } else showWarningOperation(mainApp.getPrimaryStage(), "изменить", "преподавателя");
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "преподавателя");
        }
    }

    @FXML
    private void handleDeleteTeacher() {
        int selectedIndex = teachersListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            teachersListView.getItems().remove(selectedIndex);
            teachersListView.getSelectionModel().clearSelection();
            textTeacher.setText("");
            textTeacher.requestFocus();
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "удалить", "преподавателя");
        }
    }

    @FXML
    private void handleClickListViewTeachers() {
        Teacher selectedTeacher = teachersListView.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            textTeacher.setText(selectedTeacher.getName());
            textTeacher.requestFocus();
        }
    }
}
