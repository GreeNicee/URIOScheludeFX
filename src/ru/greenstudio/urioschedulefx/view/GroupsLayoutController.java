package ru.greenstudio.urioschedulefx.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.model.Group;
import ru.greenstudio.urioschedulefx.model.Lesson;

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
    private ListCell<String> listGroupsCell;

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

        nameLessonColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        hoursLessonColumn.setCellValueFactory(cellData -> cellData.getValue().getHoursProperty().asObject());

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
            for (int i = 0; i < group.getLessons().size(); i++) {
                lessonTableView.getItems().add(i, group.getLessons().get(i));
            }
        } else {
            // Если Person = null, то убираем весь текст.
            lessonTableView.getItems().clear();
        }
    }
}
