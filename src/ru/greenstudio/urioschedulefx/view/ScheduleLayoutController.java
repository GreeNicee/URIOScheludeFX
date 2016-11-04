package ru.greenstudio.urioschedulefx.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.model.*;

import java.util.List;

import static ru.greenstudio.urioschedulefx.Utils.Alerts.showNotReadyAlert;

public class ScheduleLayoutController {
    @FXML
    private ListView<Day> listDays;
    @FXML
    private TableView<Lecture> tableLectures;
    @FXML
    private TableColumn<Lecture, Integer> lectureColumn;
    @FXML
    private TableColumn<Lecture, String> cabsColumn;
    @FXML
    private TableColumn<Lecture, String> lessonsColumn;
    @FXML
    private TableColumn<Lecture, String> teacherColumn;
    @FXML
    private ChoiceBox<String> comboGroups;
    @FXML
    private ComboBox<String> comboCabs;
    @FXML
    private ComboBox<String> comboLessons;


    private MainApp mainApp;

    public ScheduleLayoutController() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        for (int i = 0; i < mainApp.getGroupsData().size(); i++) {
            comboGroups.getItems().add(i, mainApp.getGroupsData().get(i).getName());
        }

        comboGroups.setOnAction(event ->
                showDayDetails(listDays.getSelectionModel().getSelectedItem()));
        comboGroups.setOnMouseClicked(event -> {
            comboGroups.getItems().clear();
            for (int i = 0; i < mainApp.getGroupsData().size(); i++) {
                comboGroups.getItems().add(i, mainApp.getGroupsData().get(i).getName());
            }
        });

        listDays.getItems().setAll(mainApp.getSchedule().getDays());

        ObservableList<String> data = FXCollections.observableArrayList();
        data.setAll(mainApp.getCabsListData());

        listDays.setCellFactory(new Callback<ListView<Day>, ListCell<Day>>() {
            @Override
            public ListCell<Day> call(ListView<Day> param) {
                return new ListCell<Day>() {
                    @Override
                    protected void updateItem(Day item, boolean empty) {
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

        lectureColumn.setCellValueFactory(cellData -> cellData.getValue().numNameProperty().asObject());
        lectureColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        cabsColumn.setCellValueFactory(cellData -> cellData.getValue().cabProperty());
        cabsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        lessonsColumn.setCellValueFactory(cellData -> cellData.getValue().lessonProperty());
        lessonsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        teacherColumn.setCellValueFactory(cellData -> cellData.getValue().teacherProperty());
        teacherColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        showDayDetails(null);
        listDays.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDayDetails(newValue)
        );

    }

    private void showDayDetails(Day day) {
        if (day != null) {
            tableLectures.getItems().clear();
            int k = 0;
            for (int i = 0; i < day.getLectures().size(); i++) {
                if (day.getLectures().get(i).getGroup().equals(comboGroups.getSelectionModel().getSelectedItem())) {
                    Lecture lecture = day.getLectures().get(i);
                    tableLectures.getItems().add(k, lecture);
                    k++;
                }
            }
        } else {
            tableLectures.getItems().clear();
        }
    }

    @FXML
    private void handleComboCabsClick() {
        if (comboCabs.getSelectionModel().getSelectedItem() == null)
            return;
        List<Lecture> lectures = mainApp.getSchedule().getDays().get(listDays.getSelectionModel().getSelectedIndex()).getLectures();
        String comboItem = comboCabs.getSelectionModel().getSelectedItem();

        for (Lecture lecture : lectures) {
            if (lecture.getNumName() == tableLectures.getSelectionModel().getSelectedItem().getNumName() &&
                    lecture.getGroup().equals(comboGroups.getSelectionModel().getSelectedItem())) {
                lecture.setCab(comboItem);
                tableLectures.getSelectionModel().getSelectedItem().setCab(comboItem);
                break;
            }
        }

        tableLectures.refresh();
        tableLectures.getSelectionModel().clearSelection();
        Platform.runLater(() -> {
            comboLessons.getSelectionModel().clearSelection();
            comboLessons.getItems().clear();
            comboCabs.getSelectionModel().clearSelection();
            comboCabs.getItems().clear();
        });
    }

    @FXML
    private void handleComboLessonsClick() {
        if (comboLessons.getSelectionModel().getSelectedItem() == null)
            return;
        List<Lecture> lectures = mainApp.getSchedule().getDays().get(listDays.getSelectionModel().getSelectedIndex()).getLectures();
        String comboItem = comboLessons.getSelectionModel().getSelectedItem();

        String lessonName = comboItem.substring(0, comboItem.indexOf('|') - 1);
        String teacherName = comboItem.substring(comboItem.indexOf('|') + 2);
        String oldLessonName = "";
        String oldTeacherName = "";

        for (Lecture lecture : lectures) {
            if (lecture.getNumName() == tableLectures.getSelectionModel().getSelectedItem().getNumName() &&
                    lecture.getGroup().equals(comboGroups.getSelectionModel().getSelectedItem())) {

                if (lecture.getLesson() != null)
                    oldLessonName = lecture.getLesson();
                else System.out.println("lesson not null");
                if (lecture.getTeacher() != null)
                    oldTeacherName = lecture.getTeacher();
                else System.out.println("teacher not null");


                lecture.setLesson(lessonName);
                tableLectures.getSelectionModel().getSelectedItem().
                        setLesson(lessonName);
                lecture.setTeacher(teacherName);
                tableLectures.getSelectionModel().getSelectedItem().setTeacher(teacherName);
                break;
            }
        }

        for (int i = 0; i < mainApp.getSchedule().getActualGroups().size(); i++) {
            boolean boo1 = false, boo2 = false;
            if (mainApp.getSchedule().getActualGroups().get(i).getName().equals(comboGroups.getSelectionModel().getSelectedItem())) {
                for (int j = 0; j < mainApp.getLessonsListData().size(); j++) {
                    if (mainApp.getLessonsListData().get(j).equals(lessonName)) {
                        mainApp.getSchedule().getActualGroups().get(i).getLessonsHours().set(j,
                                mainApp.getSchedule().getActualGroups().get(i).getLessonsHours().get(j) + 2);
                        boo1 = true;
                    }

                    if (oldLessonName.equals("")) {
                        boo2 = true;
                    } else if (mainApp.getLessonsListData().get(j).equals(oldLessonName)) {
                        mainApp.getSchedule().getActualGroups().get(i).getLessonsHours().set(j,
                                mainApp.getSchedule().getActualGroups().get(i).getLessonsHours().get(j) - 2);
                        boo2 = true;
                    }
                    if (boo1 && boo2)
                        break;
                }
                if (boo1 && boo2)
                    break;
            }
        }

        for (int i = 0; i < mainApp.getSchedule().getActualTeachers().size(); i++) {
            boolean boo1 = false, boo2 = false;
            if (mainApp.getSchedule().getActualTeachers().get(i).getName().equals(teacherName)) {
                for (int j = 0; j < mainApp.getSchedule().getActualTeachers().get(i).getLessons().size(); j++) {
                    if (mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).getName().equals(lessonName)) {
                        mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).setHours(
                                mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).getHours() + 2);
                        boo1 = true;
                    }
                }
            }

            if (teacherName.equals("")) {
                boo2 = true;
            } else if (mainApp.getSchedule().getActualTeachers().get(i).getName().equals(oldTeacherName)) {
                for (int j = 0; j < mainApp.getSchedule().getActualTeachers().get(i).getLessons().size(); j++) {
                    if (mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).getName().equals(oldLessonName)) {
                        mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).setHours(
                                mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).getHours() - 2);
                        boo2 = true;
                    }
                }
            }

            if (boo1 && boo2)
                break;
        }

        tableLectures.refresh();
        tableLectures.getSelectionModel().clearSelection();
        Platform.runLater(() -> {
            comboLessons.getSelectionModel().clearSelection();
            comboLessons.getItems().clear();
            comboCabs.getSelectionModel().clearSelection();
            comboCabs.getItems().clear();
        });
    }

    @FXML
    private void handleTableLecturesClick() {
        if (tableLectures.getSelectionModel().getSelectedItem() == null)
            return;

        Schedule schedule = mainApp.getSchedule();
        List<Lecture> lectures = mainApp.getSchedule().getDays().get(
                listDays.getSelectionModel().getSelectedIndex()).getLectures();

        ObservableList<String> dataCabs = FXCollections.observableArrayList();
        for (int i = 0; i < mainApp.getCabsListData().size(); i++) {
            dataCabs.add(mainApp.getCabsListData().get(i));
        }


        for (Lecture lecture : lectures) {
            for (int j = 0; j < dataCabs.size(); j++) {
                if (lecture.getCab().equals(dataCabs.get(j)) && lecture.getNumName()
                        == tableLectures.getSelectionModel().getSelectedItem().getNumName()) {
                    dataCabs.remove(j);
                    break;
                }
            }
        }
        comboCabs.setItems(dataCabs);

        ObservableList<String> dataLessons = FXCollections.observableArrayList();

        for (int i = 0; i < mainApp.getSchedule().getMaxGroups().size(); i++) {
            if (mainApp.getSchedule().getMaxGroups().get(i).getName().equals(comboGroups.getSelectionModel().getSelectedItem())) {
                for (int j = 0; j < mainApp.getSchedule().getMaxGroups().get(i).getLessonsHours().size(); j++) {
                    if (mainApp.getSchedule().getMaxGroups().get(i).getLessonsHours().get(j) >
                            mainApp.getSchedule().getActualGroups().get(i).getLessonsHours().get(j) &&
                            mainApp.getSchedule().getMaxGroups().get(i).getLessonsHours().get(j) > 0)
                        dataLessons.add(mainApp.getLessonsListData().get(j));
                }
                break;
            }
        }
        ObservableList<Teacher> dataTeachers = FXCollections.observableArrayList();
        ObservableList<Teacher> teachersData = mainApp.getTeachersData();

        for (Teacher teacher : teachersData) {
            if (teacher.getLessons().size() > 0)
                dataTeachers.add(new Teacher(teacher.getName(), FXCollections.observableArrayList()));
            for (int j = 0; j < teacher.getLessons().size(); j++) {
                if (teacher.getLessons().get(j).getHours() > 0) {
                    dataTeachers.get(dataTeachers.size() - 1).getLessons().
                            add(teacher.getLessons().get(j));
                }
            }
        }

        ObservableList<String> dataNiceLessons = FXCollections.observableArrayList();
        for (String dataLesson : dataLessons) {
            for (Teacher dataTeacher : dataTeachers) {
                for (int k = 0; k < dataTeacher.getLessons().size(); k++) {
                    if (dataLesson.equals(dataTeacher.getLessons().get(k).getName())) {
                        dataNiceLessons.add(dataTeacher.getLessons().get(k).getName() + " | " +
                                dataTeacher.getName());
                    }
                }
            }
        }

        for (int i = 0; i < lectures.size(); i++) {
            for (int j = 0; j < dataNiceLessons.size(); j++) {
                if (lectures.get(i).getNumName() != tableLectures.getSelectionModel().getSelectedItem().getNumName() &&
                        !lectures.get(i).getGroup().equals(
                                tableLectures.getSelectionModel().getSelectedItem().getGroup())) {
                    if (lectures.get(i).getTeacher().equals(
                            dataNiceLessons.get(j).substring(dataNiceLessons.get(j).indexOf('|') + 2)
                    ) && lectures.get(i).getNumName() == tableLectures.getSelectionModel().getSelectedItem().getNumName()) {
                        dataNiceLessons.remove(j);
                        --j;
                    }
                }
            }
        }


        Group groupActual = new Group("", FXCollections.observableArrayList(), FXCollections.observableArrayList());
        Group groupMax = new Group("", FXCollections.observableArrayList(), FXCollections.observableArrayList());

        for (int i = 0; i < mainApp.getSchedule().getMaxGroups().size(); i++) {
            if (comboGroups.getSelectionModel().getSelectedItem().equals(mainApp.getSchedule().getMaxGroups().get(i).getName())) {
                groupActual = new Group(
                        mainApp.getSchedule().getActualGroups().get(i).getName(),
                        mainApp.getLessonsListData(),
                        mainApp.getSchedule().getActualGroups().get(i).getLessonsHours()
                );

                groupMax = new Group(
                        mainApp.getSchedule().getMaxGroups().get(i).getName(),
                        mainApp.getLessonsListData(),
                        mainApp.getSchedule().getMaxGroups().get(i).getLessonsHours()
                );
                break;
            }
        }
        for (int i = 0; i < dataNiceLessons.size(); i++) {
            boolean boo = false;
            int k = 0;
            for (int j = 0; j < groupActual.getLessonsNames().size(); j++) {
                String lessName = dataNiceLessons.get(i).substring(0, dataNiceLessons.get(i).indexOf("|") - 1);

                if (lessName.equals(groupActual.getLessonsNames().get(j))) {
                    if (groupActual.getLessonsHours().get(j) >= groupMax.getLessonsHours().get(j) ||
                            groupMax.getLessonsHours().get(j) == 0) {
                        boo = true;
                        k = i;
                        break;
                    }
                    boo = false;
                    break;
                } else {
                    boo = true;
                    k = i;
                }
            }
            if (boo)
                dataNiceLessons.remove(k);
        }

        for (int i = 0; i < dataNiceLessons.size(); i++) {
            boolean boo = false;
            int k = 0;
            exit:
            for (int j = 0; j < schedule.getMaxTeachers().size(); j++) {
                String teacherName = dataNiceLessons.get(i).substring(dataNiceLessons.get(i).indexOf("|") + 2);
                String lessName = dataNiceLessons.get(i).substring(0, dataNiceLessons.get(i).indexOf("|") - 1);

                if (teacherName.equals(schedule.getMaxTeachers().get(j).getName())) {
                    for (int l = 0; l < schedule.getMaxTeachers().get(j).getLessons().size(); l++) {
                        if (schedule.getActualTeachers().get(j).getLessons().get(l).getName().equals(lessName)) {
                            if (schedule.getActualTeachers().get(j).getLessons().get(l).getHours() >=
                                    schedule.getMaxTeachers().get(j).getLessons().get(l).getHours() ||
                                    schedule.getMaxTeachers().get(j).getLessons().get(l).getHours() == 0) {
                                boo = true;
                                k = i;
                                break exit;
                            }
                        }
                    }
                }
            }
            if (boo) {
                dataNiceLessons.remove(k);
                --i;
            }
        }
        System.out.println(dataNiceLessons);

        comboLessons.setItems(dataNiceLessons);
    }


    @FXML
    private void handleAddLecture() {
        int index = tableLectures.getItems().size() + 1;
        String groupName = comboGroups.getSelectionModel().getSelectedItem();
        if (groupName == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Нельзя добавить пару для несуществующей группы");
            alert.setContentText("Выберите группу=)");
            alert.showAndWait();
            return;
        }

        int listIndex = listDays.getSelectionModel().getSelectedIndex();
        if (listIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Нельзя добавить пару для несуществующего дня недели");
            alert.setContentText("Выберите день недели=)");
            alert.showAndWait();
            return;
        }

        mainApp.getSchedule().getDays().get(listIndex).getLectures().
                add(new Lecture(index, groupName, "",
                        "", ""));
        tableLectures.getItems().add(new Lecture(index, groupName, "", "", ""));
        tableLectures.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleDeleteLecture() {
        int indexDay = listDays.getSelectionModel().getSelectedIndex();
        int indexLecture = tableLectures.getItems().size();

        if (indexLecture == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ничего не осталось");
            alert.setHeaderText("Вы уже удалили все пары (а может еще и не создавали)");
            alert.setContentText("Добавьте пару, чтобы ее удалить=)");
            alert.showAndWait();
            return;
        }
        String groupName = comboGroups.getSelectionModel().getSelectedItem();
        String teacherName = tableLectures.getItems().get(indexLecture - 1).getTeacher();
        String lessonName = tableLectures.getItems().get(indexLecture - 1).getLesson();

        for (int i = 0; i < mainApp.getSchedule().getActualGroups().size(); i++) {
            if (mainApp.getSchedule().getActualGroups().get(i).getName().equals(groupName)) {
                for (int j = 0; j < mainApp.getLessonsListData().size(); j++) {
                    if (mainApp.getLessonsListData().get(j).equals(lessonName)) {
                        mainApp.getSchedule().getActualGroups().get(i).getLessonsHours().set(j,
                                mainApp.getSchedule().getActualGroups().get(i).getLessonsHours().get(j) - 2);
                        break;
                    }
                }
                break;
            }
        }

        for (int i = 0; i < mainApp.getSchedule().getActualTeachers().size(); i++) {
            if (mainApp.getSchedule().getActualTeachers().get(i).getName().equals(teacherName)) {
                for (int j = 0; j < mainApp.getSchedule().getActualTeachers().get(i).getLessons().size(); j++) {
                    if (mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).getName().equals(lessonName)) {
                        mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).setHours(
                                mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).getHours() - 2);
                        break;
                    }
                }
                break;
            }
        }

        tableLectures.getItems().remove(indexLecture - 1);

        List<Lecture> lectures = mainApp.getSchedule().getDays().get(indexDay).getLectures();
        for (int i = 0; i < lectures.size(); i++) {
            if (lectures.get(i).getNumName() == indexLecture && lectures.get(i).getGroup().equals(groupName)) {
                lectures.remove(i);
                break;
            }
        }

        tableLectures.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleRefreshData() {
        System.out.println(mainApp.getSchedule());

//        mainApp.showStatistics();
    }

    @FXML//TODO не доделал
    private void handleAutoCreateSchedule() {
        showNotReadyAlert(mainApp.getPrimaryStage());
        /*for (Day day : mainApp.getSchedule().getDays()) {
            day.getLectures().clear();
        }

        for (Group group : mainApp.getSchedule().getActualGroups()) {
            for (int i = 0; i < group.getLessonsHours().size(); i++) {
                group.getLessonsHours().set(i, 0);
            }
        }

        for (Teacher teacher : mainApp.getSchedule().getActualTeachers()) {
            for (Lesson lesson : teacher.getLessons()) {
                lesson.setHours(0);
            }
        }

        for (Group group : mainApp.getGroupsData()) {
            int hoursCount = 0;

            for (Integer lessonHours : group.getLessonsHours()) {
                hoursCount += lessonHours;
            }

            int lecturesInDay = hoursCount / 5;

            int lecturesMod = hoursCount % 5;

            for (Day day : mainApp.getSchedule().getDays()) {
                for (int i = 0; i < lecturesInDay; i++) {
                    Lecture lecture = new Lecture(i, "", "", "", "");

                    exit:
                    for (Group actualGroup : mainApp.getSchedule().getActualGroups()) {
                        if (actualGroup.getName().equals(group.getName())) {
                            for (int j = 0; j < actualGroup.getLessonsHours().size(); j++) {
                                if (actualGroup.getLessonsHours().get(j) < group.getLessonsHours().get(j)) {
                                    actualGroup.getLessonsHours().set(j, actualGroup.getLessonsHours().get(j) + 2);
                                    lecture.setGroup(group.getName());
                                    lecture.setLesson(mainApp.getLessonsListData().get(j));

                                    break exit;
                                }
                            }
                        }
                    }

                    for (Teacher teacher : mainApp.getSchedule().getActualTeachers()) {
                        for (Lesson lesson : teacher.getLessons()) {

                        }
                    }
                }
            }

            System.out.printf("Группа: %s\nПар в день: %d\n Остаток пар: %d\n", group.getName(), lecturesInDay, lecturesMod);
        }*/
    }
}
