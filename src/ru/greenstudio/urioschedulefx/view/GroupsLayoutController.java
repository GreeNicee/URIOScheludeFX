package ru.greenstudio.urioschedulefx.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.model.Group;
import ru.greenstudio.urioschedulefx.model.Lesson;

import static ru.greenstudio.urioschedulefx.Utils.Alerts.alreadyInGroupData;
import static ru.greenstudio.urioschedulefx.Utils.Alerts.showWarningOperation;
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
        hoursLessonColumn.setOnEditCommit(
                event -> groupsListView.getSelectionModel().getSelectedItem().getLessonsHours().set(
                        event.getTablePosition().getRow(),
                        event.getNewValue()
                )
        );

        showGroupDetails(null);
        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        groupsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showGroupDetails(newValue)
        );
    }

    private void showGroupDetails(Group group) {
        if (group != null) {
            lessonTableView.getItems().clear();
            for (int i = 0; i < group.getLessonsNames().size(); i++) {
                Lesson lesson = new Lesson(group.getLessonsNames().get(i), group.getLessonsHours().get(i));
                lessonTableView.getItems().add(i, lesson);
            }
        } else {
            // Если Person = null, то убираем весь текст.
            lessonTableView.getItems().clear();
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
        }
    }
}
