package ru.greenstudio.urioschedulefx.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.model.Day;
import ru.greenstudio.urioschedulefx.model.Group;
import ru.greenstudio.urioschedulefx.model.Lecture;
import ru.greenstudio.urioschedulefx.model.Lesson;

import static ru.greenstudio.urioschedulefx.Utils.Alerts.alreadyInGroupData;
import static ru.greenstudio.urioschedulefx.Utils.Alerts.showWarningOperation;
import static ru.greenstudio.urioschedulefx.Utils.Funcs.*;
import static ru.greenstudio.urioschedulefx.Utils.IsInputOk.isTextFieldOk;

public class GroupsLayoutController {
    @FXML
    private ListView<Group> groupsListView;

    @FXML
    private TableView<Lesson> lessonTableView;
    @FXML
    private TextField textGroup;
    @FXML
    private TableColumn<Lesson, String> nameLessonColumn;
    @FXML
    private TableColumn<Lesson, Integer> hoursLessonColumn;
    @FXML
    private Label labelLesson;
    @FXML
    private TextField textLesson;

    private MainApp mainApp;

    public GroupsLayoutController() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        ObservableList<String> groupsNames = FXCollections.observableArrayList();
        for (int i = 0; i < mainApp.getGroupsData().size(); i++) {
            groupsNames.add(mainApp.getGroupsData().get(i).getName());
        }

        groupsListView.setItems(mainApp.getGroupsData());

        groupsListView.setCellFactory(new Callback<ListView<Group>, ListCell<Group>>() {
            @Override
            public ListCell<Group> call(ListView<Group> param) {
                return new ListCell<Group>() {
                    @Override
                    protected void updateItem(Group item, boolean empty) {
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
//        hoursLessonColumn.setOnEditCommit(
//                event -> groupsListView.getSelectionModel().getSelectedItem().getLessonsHours().set(
//                        event.getTablePosition().getRow(), event.getNewValue()
//                )
//        );

        showGroupDetails(null);
        groupsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showGroupDetails(newValue));

        textGroup.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                handleAddGroup();
            }
        });

        textLesson.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                handleEditLesson();
            }
        });

        textLesson.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();

            if (!checkNumeric(character))
                event.consume();
        });

        mainApp.setLessonTableView(this.lessonTableView);
    }

    private void showGroupDetails(Group group) {
        if (group != null) {
            lessonTableView.getItems().clear();
            for (int i = 0; i < mainApp.getLessonsListData().size(); i++) {
                Lesson lesson = new Lesson(mainApp.getLessonsListData().get(i), group.getLessonsHours().get(i));
                lessonTableView.getItems().add(i, lesson);
            }
        } else {
            // Если Person = null, то убираем весь текст.
            lessonTableView.getItems().clear();
        }
    }

    @FXML
    private void handleEditLesson() {
        Lesson selectedLesson = lessonTableView.getSelectionModel().getSelectedItem();

        if (selectedLesson != null) {
            if (isTextFieldOk(textLesson)) {
                Lesson oldLesson = new Lesson(selectedLesson.getName(), selectedLesson.getHours());
                Group actualGroup = mainApp.getSchedule().getActualGroups().get(
                        groupsListView.getSelectionModel().getSelectedIndex());

                Group maxGroup = mainApp.getGroupsData().get(groupsListView.getSelectionModel().getSelectedIndex());

                int lessonHours = Integer.parseInt(textLesson.getText());
                Lesson newLesson = new Lesson(selectedLesson.getName(), lessonHours);
                selectedLesson.setHours(lessonHours);

                mainApp.getGroupsData().get(groupsListView.getSelectionModel().getSelectedIndex()).
                        getLessonsHours().set(lessonTableView.getSelectionModel().getSelectedIndex(), selectedLesson.getHours());

                if (oldLesson.getHours() - newLesson.getHours() > 0) {
                    System.out.println("if > 0");
                    checkGroupLessons(newLesson, oldLesson, maxGroup, actualGroup,
                            groupsListView.getSelectionModel().getSelectedIndex());
                }

                lessonTableView.getSelectionModel().clearSelection();
                textLesson.setText("");
                textLesson.requestFocus();
            } else showWarningOperation(mainApp.getPrimaryStage(), "изменить", "предмет");
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "предмет");
        }
    }

    private void checkGroupLessons(Lesson newLesson, Lesson oldLesson, Group maxGroup, Group actualGroup, int lessonNum) {
        int lessonHours = oldLesson.getHours() - newLesson.getHours();
        while (lessonHours > 0) {
            System.out.println(actualGroup.getLessonsHours().get(lessonNum) + " > "
                    + maxGroup.getLessonsHours().get(lessonNum));
            exit:
            //Если у группы были назначены пары
            if (actualGroup.getLessonsHours().get(lessonNum) > maxGroup.getLessonsHours().get(lessonNum)) {
                for (Day day : mainApp.getSchedule().getDays()) {
                    for (Lecture lecture : day.getLectures()) {
                        if (lecture.getGroup().equals(actualGroup.getName()) && lecture.getLesson().equals(newLesson.getName())) {
                            for (int i = 0; i < mainApp.getTeachersData().size(); i++) {
                                for (int j = 0; j < mainApp.getTeachersData().get(i).getLessons().size(); j++) {
                                    if (lecture.getLesson().equals(mainApp.getTeachersData().get(i).getLessons().get(j).getName()) &&
                                            lecture.getTeacher().equals(mainApp.getTeachersData().get(i).getName())) {
                                        System.out.println("if");
                                        mainApp.getTeachersData().get(i).getLessons().get(j).setHours(
                                                mainApp.getTeachersData().get(i).getLessons().get(j).getHours() - 2);

                                        mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).setHours(
                                                mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).getHours() - 2);

                                        actualGroup.getLessonsHours().set(lessonNum, actualGroup.getLessonsHours().get(lessonNum) - 2);

                                        lessonHours -= 2;

                                        lecture.setTeacher("");
                                        lecture.setLesson("");
                                        break exit;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {//Если удалили все назначенные часы - чекаем нужно ли у преподов убирать часы
                int teachersLessonHours = 0, groupsLessonHours = 0;
                for (Lesson lesson : getTeachersLessonsData(mainApp)) {
                    if (lesson.getName().equals(oldLesson.getName()))
                        teachersLessonHours += lesson.getHours();
                }

                for (Lesson lesson : getGroupsLessonsData(mainApp)) {
                    if (lesson.getName().equals(oldLesson.getName()))
                        groupsLessonHours += lesson.getHours();
                }

                if (teachersLessonHours == groupsLessonHours + lessonHours) {
                    //Если удалили не назначенные часы - ищем преподов с неназначенными часами)
                    for (int i = 0; i < mainApp.getTeachersData().size(); i++) {
                        for (int j = 0; j < mainApp.getTeachersData().get(i).getLessons().size(); j++) {
                            if (mainApp.getTeachersData().get(i).getLessons().get(j).getName().equals(oldLesson.getName())) {
                                if (mainApp.getTeachersData().get(i).getLessons().get(j).getHours() >
                                        mainApp.getSchedule().getActualTeachers().get(i).getLessons().get(j).getHours()) {
                                    lessonHours -= 2;
                                    mainApp.getTeachersData().get(i).getLessons().get(j).setHours(
                                            mainApp.getTeachersData().get(i).getLessons().get(j).getHours() - 2);
                                    break exit;
                                }
                            }
                        }
                    }
                } else {
                    lessonHours -= 2;
                }

            }

        }

    }

    @FXML
    private void handleDeleteLesson() {
        Lesson selectedLesson = lessonTableView.getSelectionModel().getSelectedItem();

        if (selectedLesson != null) {
            Lesson oldLesson = new Lesson(selectedLesson.getName(), selectedLesson.getHours());
            Group actualGroup = mainApp.getSchedule().getActualGroups().get(
                    groupsListView.getSelectionModel().getSelectedIndex());

            Group maxGroup = mainApp.getGroupsData().get(groupsListView.getSelectionModel().getSelectedIndex());

            selectedLesson.setHours(0);
            Lesson newLesson = new Lesson(selectedLesson.getName(), 0);

            mainApp.getGroupsData().get(groupsListView.getSelectionModel().getSelectedIndex()).
                    getLessonsHours().set(lessonTableView.getSelectionModel().getSelectedIndex(), selectedLesson.getHours());

            if (oldLesson.getHours() - newLesson.getHours() > 0) {
                System.out.println("if > 0");
                checkGroupLessons(newLesson, oldLesson, maxGroup, actualGroup,
                        groupsListView.getSelectionModel().getSelectedIndex());
            }

            lessonTableView.getSelectionModel().clearSelection();
            textLesson.setText("");
            textLesson.requestFocus();
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "\"обнулить\"", "предмет");
        }
    }

    @FXML
    private void handleClickLessonsTableView() {
        Lesson selectedLesson = lessonTableView.getSelectionModel().getSelectedItem();
        if (selectedLesson != null) {
            labelLesson.setText(selectedLesson.getName());
            textLesson.setText(String.valueOf(selectedLesson.getHours()));
            textLesson.requestFocus();
        } else {
            labelLesson.setText("Предмет");
            textLesson.setText("");
        }
    }

    @FXML
    private void handleAddGroup() {
        if (!isTextFieldOk(textGroup)) {
            showWarningOperation(mainApp.getPrimaryStage(), "добавить", "группу");
            return;
        }
        if (!alreadyInGroupData(mainApp.getGroupsData(), textGroup.getText(), mainApp.getPrimaryStage(), "группу")) {
            String groupName = textGroup.getText();
            ObservableList<Integer> list = FXCollections.observableArrayList();
            for (int i = 0; i < mainApp.getLessonsListData().size(); i++) {
                list.add(0);
            }

            ObservableList<Integer> list1 = FXCollections.observableArrayList();
            for (int i = 0; i < mainApp.getLessonsListData().size(); i++) {
                list1.add(0);
            }
            mainApp.getGroupsData().add(new Group(groupName, mainApp.getLessonsListData(), list));
            mainApp.getSchedule().getActualGroups().add(new Group(groupName, mainApp.getLessonsListData(), list1));
            groupsListView.getSelectionModel().clearSelection();
            textGroup.setText("");
            textGroup.requestFocus();
        }
    }

    @FXML
    private void handleEditGroup() {
        Group selectedGroup = groupsListView.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            if (isTextFieldOk(textGroup)) {
                if (!alreadyInGroupData(mainApp.getGroupsData(), textGroup.getText(), mainApp.getPrimaryStage(), "группу")) {
                    String oldGroupName = groupsListView.getSelectionModel().getSelectedItem().getName();
                    String groupName = textGroup.getText();
                    selectedGroup.setName(groupName);
                    mainApp.getGroupsData().get(groupsListView.getSelectionModel().getSelectedIndex()).setName(groupName);
                    mainApp.getSchedule().getActualGroups().get(
                            groupsListView.getSelectionModel().getSelectedIndex()).setName(groupName);
                    groupsListView.refresh();
                    groupsListView.getSelectionModel().clearSelection();
                    textGroup.setText("");
                    textGroup.requestFocus();
                    for (int i = 0; i < mainApp.getSchedule().getDays().size(); i++) {
                        for (int j = 0; j < mainApp.getSchedule().getDays().get(i).getLectures().size(); j++) {
                            if (mainApp.getSchedule().getDays().get(i).getLectures().get(j).getGroup().equals(oldGroupName))
                                mainApp.getSchedule().getDays().get(i).getLectures().get(j).setGroup(groupName);
                        }
                    }
                }
            } else showWarningOperation(mainApp.getPrimaryStage(), "изменить", "группу");
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "группу");
        }
    }

    @FXML//TODO ПЕРЕДЕЛАТЬ
    private void handleDeleteGroup() {
        int selectedIndex = groupsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Group actualGroup = mainApp.getSchedule().getActualGroups().get(
                    groupsListView.getSelectionModel().getSelectedIndex());
            Group maxGroup = mainApp.getGroupsData().get(groupsListView.getSelectionModel().getSelectedIndex());

            String groupName = maxGroup.getName();

            for (int i = 0; i < maxGroup.getLessonsHours().size(); i++) {
                Lesson oldLesson = new Lesson(mainApp.getLessonsListData().get(i),
                        maxGroup.getLessonsHours().get(i));

                mainApp.getGroupsData().get(groupsListView.getSelectionModel().getSelectedIndex()).
                        getLessonsHours().set(i, 0);
                Lesson newLesson = new Lesson(mainApp.getLessonsListData().get(i), 0);

                if (oldLesson.getHours() - newLesson.getHours() > 0) {
                    checkGroupLessons(newLesson, oldLesson, maxGroup, actualGroup, i);
                }
            }

            groupsListView.getItems().remove(selectedIndex);
            mainApp.getSchedule().getActualGroups().remove(selectedIndex);
            groupsListView.getSelectionModel().clearSelection();
            textGroup.setText("");
            textGroup.requestFocus();

            for (Day day : mainApp.getSchedule().getDays()) {
                for (int i = 0; i < day.getLectures().size(); i++) {
                    if (day.getLectures().get(i).getGroup().equals(groupName)) {
                        day.getLectures().remove(i);
                        --i;
                    }
                }
            }

        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "удалить", "группу");
        }
    }

    @FXML
    private void handleClickListViewGroups() {
        Group selectedGroup = groupsListView.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            textGroup.setText(selectedGroup.getName());
            textGroup.requestFocus();
        }
    }

}