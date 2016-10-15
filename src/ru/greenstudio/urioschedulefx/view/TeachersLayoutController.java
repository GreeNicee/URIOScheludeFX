package ru.greenstudio.urioschedulefx.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.Utils.AutoCompleteComboboxListener;
import ru.greenstudio.urioschedulefx.model.Lesson;
import ru.greenstudio.urioschedulefx.model.Teacher;

public class TeachersLayoutController {
    @FXML
    private ListView<Teacher> teachersListView;
    @FXML
    private TableView<Lesson> lessonTableView;
    @FXML
    private TextField textGroup;
    @FXML
    private ComboBox comboBoxTeacher;
    @FXML
    private TableColumn<Lesson, String> nameLessonColumn;
    @FXML
    private TableColumn<Lesson, Integer> hoursLessonColumn;

    private MainApp mainApp;

    public TeachersLayoutController() {
    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        comboBoxTeacher.getItems().addAll("Андреев Иван Александрович", "Писькин Василий иванович", "Пчелкин Евгений Грибович"
                , "abasdada", "aafdsdsf", "asadsfd", "aghfhg", "awervbc", "artnbvn", "aqwb", "apgkh");
        new AutoCompleteComboboxListener(comboBoxTeacher);

    }

}
