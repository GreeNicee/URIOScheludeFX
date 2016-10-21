package ru.greenstudio.urioschedulefx.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.model.Day;
import ru.greenstudio.urioschedulefx.model.Lecture;
import ru.greenstudio.urioschedulefx.model.Schedule;

import java.util.List;

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

        lectureColumn.setCellValueFactory(cellData -> cellData.getValue().getNumName().asObject());
        lectureColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        cabsColumn.setCellValueFactory(cellData -> cellData.getValue().getCab());
        cabsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        lessonsColumn.setCellValueFactory(cellData -> cellData.getValue().getLesson());
        lessonsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

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
            if (lecture.getNumName().get() == tableLectures.getSelectionModel().getSelectedItem().getNumName().get() &&
                    lecture.getGroup().equals(comboGroups.getSelectionModel().getSelectedItem())) {
                lecture.setCab(new SimpleStringProperty(comboItem));
                tableLectures.getSelectionModel().getSelectedItem().setCab(
                        new SimpleStringProperty(comboItem));
                break;
            }
        }

        tableLectures.refresh();
        tableLectures.getSelectionModel().clearSelection();
        //TODO КРИТ, котоырй не влияет ни на что)
        comboCabs.getSelectionModel().clearSelection();
        comboCabs.getItems().clear();
    }

    @FXML
    private void handleComboLessonsClick() {

    }

    @FXML
    private void handleTableLecturesClick() {
        Schedule schedule = mainApp.getSchedule();
        List<Lecture> lectures = mainApp.getSchedule().getDays().get(listDays.getSelectionModel().getSelectedIndex()).getLectures();

        ObservableList<String> dataCabs = FXCollections.observableArrayList();
        for (int i = 0; i < mainApp.getCabsListData().size(); i++) {
            dataCabs.add(mainApp.getCabsListData().get(i));
        }


        for (Lecture lecture : lectures) {
            for (int j = 0; j < dataCabs.size(); j++) {
                if (lecture.getCab().get().equals(dataCabs.get(j)) && lecture.getNumName().get()
                        == tableLectures.getSelectionModel().getSelectedItem().getNumName().get()) {
                    dataCabs.remove(j);
                    break;
                }
            }
        }
        comboCabs.setItems(dataCabs);

        ObservableList<String> dataLessons = FXCollections.observableArrayList();
        for (int i = 0; i < schedule.getMaxGroupLessons().size(); i++) {
            if (schedule.getMaxGroupLessons().get(i).getHours() > schedule.getActualGroupLessons().get(i).getHours())
                dataLessons.add(schedule.getMaxGroupLessons().get(i).getName());
        }

        comboLessons.setItems(dataLessons);
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
                add(new Lecture(new SimpleIntegerProperty(index), groupName, "",
                        new SimpleStringProperty(""), new SimpleStringProperty("")));
        tableLectures.getItems().add(new Lecture(new SimpleIntegerProperty(index), groupName, "",
                new SimpleStringProperty(""), new SimpleStringProperty("")));
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

        tableLectures.getItems().remove(indexLecture - 1);

        List<Lecture> lectures = mainApp.getSchedule().getDays().get(indexDay).getLectures();
        for (int i = 0; i < lectures.size(); i++) {
            if (lectures.get(i).getNumName().get() == indexLecture && lectures.get(i).getGroup().equals(groupName)) {
                lectures.remove(i);
                break;
            }
        }

        tableLectures.getSelectionModel().clearSelection();
        System.out.println(mainApp.getSchedule());
    }

    @FXML
    private void handleRefreshData() {

    }

    @FXML
    private void handleAutoCreateSchedule() {

    }
}
