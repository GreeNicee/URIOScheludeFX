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
import ru.greenstudio.urioschedulefx.model.Group;
import ru.greenstudio.urioschedulefx.model.Lesson;

import static ru.greenstudio.urioschedulefx.Utils.Alerts.alreadyInGroupData;
import static ru.greenstudio.urioschedulefx.Utils.Alerts.showWarningOperation;
import static ru.greenstudio.urioschedulefx.Utils.Funcs.checkLessonsData;
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
                int lessonHours = Integer.parseInt(textLesson.getText());
                selectedLesson.setHours(lessonHours);
                System.out.println(selectedLesson);
                mainApp.getGroupsData().get(groupsListView.getSelectionModel().getSelectedIndex()).
                        getLessonsHours().set(lessonTableView.getSelectionModel().getSelectedIndex(), selectedLesson.getHours());
                lessonTableView.getSelectionModel().clearSelection();
                textLesson.setText("");
                textLesson.requestFocus();

                Lesson newLesson = new Lesson(oldLesson.getName(), selectedLesson.getHours() - oldLesson.getHours());
                ObservableList<Lesson> lessonsMaxData = mainApp.getMaxLessonsData();
                ObservableList<Lesson> lessonsData = mainApp.getLessonsData();
                checkLessonsData(selectedLesson, newLesson, lessonsMaxData, lessonsData);

            } else showWarningOperation(mainApp.getPrimaryStage(), "изменить", "предмет");
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "предмет");
        }
    }

    @FXML
    private void handleDeleteLesson() {
        Lesson selectedLesson = lessonTableView.getSelectionModel().getSelectedItem();

        if (selectedLesson != null) {
            Lesson oldLesson = new Lesson(selectedLesson.getName(), selectedLesson.getHours());
            int lessonHours = 0;
            selectedLesson.setHours(lessonHours);
            mainApp.getGroupsData().get(groupsListView.getSelectionModel().getSelectedIndex()).
                    getLessonsHours().set(lessonTableView.getSelectionModel().getSelectedIndex(), selectedLesson.getHours());
            lessonTableView.getSelectionModel().clearSelection();
            textLesson.setText("");
            textLesson.requestFocus();

            Lesson newLesson = new Lesson(oldLesson.getName(), selectedLesson.getHours() - oldLesson.getHours());
            ObservableList<Lesson> lessonsMaxData = mainApp.getMaxLessonsData();
            ObservableList<Lesson> lessonsData = mainApp.getLessonsData();
            checkLessonsData(selectedLesson, newLesson, lessonsMaxData, lessonsData);
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
            mainApp.getGroupsData().add(new Group(groupName, mainApp.getLessonsListData(), list));
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
                    String groupName = textGroup.getText();
                    selectedGroup.setName(groupName);
                    mainApp.getGroupsData().set(groupsListView.getSelectionModel().getSelectedIndex(), selectedGroup);
                    groupsListView.getSelectionModel().clearSelection();
                    textGroup.setText("");
                    textGroup.requestFocus();
                }
            } else showWarningOperation(mainApp.getPrimaryStage(), "изменить", "группу");
        } else {
            showWarningOperation(mainApp.getPrimaryStage(), "изменить", "группу");
        }
    }

    @FXML
    private void handleDeleteGroup() {
        int selectedIndex = groupsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            groupsListView.getItems().remove(selectedIndex);
            groupsListView.getSelectionModel().clearSelection();
            textGroup.setText("");
            textGroup.requestFocus();
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
