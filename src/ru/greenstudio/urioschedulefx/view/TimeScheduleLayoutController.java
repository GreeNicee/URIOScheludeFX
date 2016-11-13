package ru.greenstudio.urioschedulefx.view;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.Utils.MaskField;

import java.util.ArrayList;

import static ru.greenstudio.urioschedulefx.Utils.IO.Files.loadLecturesTime;

public class TimeScheduleLayoutController {
    private MainApp mainApp;
    @FXML
    private GridPane grid;

    private ArrayList<MaskField> lecturesData = new ArrayList<>(8);

    public TimeScheduleLayoutController() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        MaskField firstLecture = new MaskField();
        firstLecture.setMask("DD:DD");
        firstLecture.setPlainText("0900");
        grid.add(firstLecture, 1, 1);

        MaskField secondLecture = new MaskField();
        secondLecture.setMask("DD:DD");
        secondLecture.setPlainText("1040");
        grid.add(secondLecture, 1, 2);

        MaskField thirdLecture = new MaskField();
        thirdLecture.setMask("DD:DD");
        thirdLecture.setPlainText("1250");
        grid.add(thirdLecture, 1, 3);

        MaskField fourthLecture = new MaskField();
        fourthLecture.setMask("DD:DD");
        fourthLecture.setPlainText("1430");
        grid.add(fourthLecture, 1, 4);

        MaskField fifthLecture = new MaskField();
        fifthLecture.setMask("DD:DD");
        fifthLecture.setPlainText("1610");
        grid.add(fifthLecture, 1, 5);

        MaskField sixthLecture = new MaskField();
        sixthLecture.setMask("DD:DD");
        sixthLecture.setPlainText("1745");
        grid.add(sixthLecture, 1, 6);

        MaskField seventhLecture = new MaskField();
        seventhLecture.setMask("DD:DD");
        seventhLecture.setPlainText("1920");
        grid.add(seventhLecture, 1, 7);

        MaskField eighthLecture = new MaskField();
        eighthLecture.setMask("DD:DD");
        eighthLecture.setPlainText("2055");
        grid.add(eighthLecture, 1, 8);

        lecturesData.add(firstLecture);
        lecturesData.add(secondLecture);
        lecturesData.add(thirdLecture);
        lecturesData.add(fourthLecture);
        lecturesData.add(fifthLecture);
        lecturesData.add(sixthLecture);
        lecturesData.add(seventhLecture);
        lecturesData.add(eighthLecture);
        mainApp.setLecturesData(lecturesData);

        loadLecturesTime(lecturesData);
    }

}