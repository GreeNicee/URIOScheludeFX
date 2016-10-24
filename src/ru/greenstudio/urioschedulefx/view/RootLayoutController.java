package ru.greenstudio.urioschedulefx.view;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.DirectoryChooser;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.model.Day;
import ru.greenstudio.urioschedulefx.model.Lecture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class RootLayoutController {
    private MainApp mainApp;

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabSchedule;
    @FXML
    private Tab tabTeachers;
    @FXML
    private Tab tabGroups;
    @FXML
    private Tab tabLessonsAndCabs;

    private String date;

    @FXML
    private void initialize() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        Calendar date1 = Calendar.getInstance();
        int diff = Calendar.MONDAY - date1.get(Calendar.DAY_OF_WEEK);
        if (!(diff > 0)) {
            diff += 7;
        }
        date1.add(Calendar.DAY_OF_MONTH, diff);

        date = String.format("%ta, %<te %<tb, %<tY", date1);

        diff = Calendar.SUNDAY - date1.get(Calendar.DAY_OF_WEEK);
        if (!(diff > 0)) {
            diff += 7;
        }
        date1.add(Calendar.DAY_OF_MONTH, diff);

        date += " - " + String.format("%ta, %<te %<tb, %<tY", date1);
    }

    public RootLayoutController() {
    }

    @FXML
    private void handleExit() {
    }

    @FXML
    private void handleInfoAuthor() {

    }

    @FXML
    private void handleInfoApp() {

    }

    @FXML
    private void handleSaveExcelGroups() {
        // создание самого excel файла в памяти
        HSSFWorkbook workbook = new HSSFWorkbook();
        // создание листа с названием "Просто лист"
        HSSFSheet sheet = workbook.createSheet("Типо группа");

        // счетчик для строк
        int rowNum = 0;

        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Дата");
        row.createCell(1).setCellValue("День недели");
        row.createCell(2).setCellValue("№ пары");
        row.createCell(3).setCellValue("Время");
        row.createCell(4).setCellValue("Дисциплина");
        row.createCell(5).setCellValue("Преподаватель");
        row.createCell(6).setCellValue("Номер аудитории");


        // заполняем лист данными
        for (int i = 0; i < mainApp.getSchedule().getDays().size(); i++) {
            Day day = mainApp.getSchedule().getDays().get(i);
            for (Lecture dataModel : mainApp.getSchedule().getDays().get(i).getLectures()) {
                createSheetHeader(sheet, ++rowNum, dataModel, day);
            }
        }

        // записываем созданный в памяти Excel документ в файл
        try (FileOutputStream out = new FileOutputStream(new File(selectDirectory().
                getAbsoluteFile() + "/Расписание для групп " + date + ".xls"))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Excel файл успешно создан!");
    }

    private static void createSheetHeader(HSSFSheet sheet, int rowNum, Lecture dataModel, Day day) {
        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue("Дата");
        row.createCell(1).setCellValue(day.getName());
        row.createCell(2).setCellValue(dataModel.getNumName());
        row.createCell(3).setCellValue("Время");
        row.createCell(4).setCellValue(dataModel.getLesson());
        row.createCell(5).setCellValue(dataModel.getTeacher());
        row.createCell(6).setCellValue(dataModel.getCab());
    }

    @FXML
    private void handleSaveExcelTeachers() {

    }

    @FXML
    private void handleSaveExcelPrint() {

    }

    @FXML
    private void handleSaveExcelAll() {

    }

    @FXML
    private void handleSaveWordGroups() {

    }

    @FXML
    private void handleSaveWordTeachers() {

    }

    @FXML
    private void handleSaveWordPrint() {

    }

    @FXML
    private void handleSaveWordAll() {

    }

    private File selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        return directoryChooser.showDialog(mainApp.getPrimaryStage());
    }
}
