package ru.greenstudio.urioschedulefx.view;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.DirectoryChooser;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.model.Day;
import ru.greenstudio.urioschedulefx.model.Group;
import ru.greenstudio.urioschedulefx.model.Lecture;
import ru.greenstudio.urioschedulefx.model.Teacher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

@SuppressWarnings("ALL")
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

    private String[] dates = new String[7];

    @FXML
    private void initialize() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        Calendar dateForExcelWord = Calendar.getInstance();
        int diff = Calendar.MONDAY - dateForExcelWord.get(Calendar.DAY_OF_WEEK);
        if (!(diff > 0)) {
            diff += 7;
        }
        dateForExcelWord.add(Calendar.DAY_OF_MONTH, diff);

        date = String.format("%02d.%02d.%4d", dateForExcelWord.get(
                Calendar.DAY_OF_MONTH), dateForExcelWord.get(Calendar.MONTH) + 1, dateForExcelWord.get(Calendar.YEAR));

        for (int i = 0; i < dates.length; i++) {
            dates[i] = String.format("%02d.%02d.%4d", dateForExcelWord.get(
                    Calendar.DAY_OF_MONTH), dateForExcelWord.get(Calendar.MONTH) + 1, dateForExcelWord.get(Calendar.YEAR));
            dateForExcelWord.add(Calendar.DAY_OF_MONTH, 1);
        }

        dateForExcelWord.add(Calendar.DAY_OF_MONTH, -1);

        date += " - " + String.format("%02d.%02d.%4d", dateForExcelWord.get(
                Calendar.DAY_OF_MONTH), dateForExcelWord.get(Calendar.MONTH) + 1, dateForExcelWord.get(Calendar.YEAR));
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

    private static void createSheetHeader(HSSFSheet sheet, int rowNum, Lecture dataModel, boolean isGroup) {
        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue(dataModel.getNumName());
        row.createCell(1).setCellValue("Время");
        row.createCell(2).setCellValue(dataModel.getLesson());
        String[] teacherPaths = dataModel.getTeacher().split(" ");
        if (isGroup) {
            String teacher = teacherPaths[0] + " ";
            for (int i = 1; i < teacherPaths.length; i++) {
                teacher += teacherPaths[i].substring(0, 1) + ".";
            }
            row.createCell(3).setCellValue(teacher);
        } else
            row.createCell(3).setCellValue(dataModel.getGroup());
        row.createCell(4).setCellValue(dataModel.getCab());

        HSSFWorkbook workbook = (HSSFWorkbook) row.getSheet().getWorkbook();

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(HSSFColor.GOLD.index);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);

        // проходим по всем ячейкам этой строки
        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
            row.getCell(j).setCellStyle(style);
        }
    }

    private void setColumnStyle(Row row, Short color, boolean isMerged) {
        HSSFWorkbook workbook = (HSSFWorkbook) row.getSheet().getWorkbook();

        // создаем шрифт
        HSSFFont font = workbook.createFont();
        // указываем, что хотим его видеть жирным
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // создаем стиль для ячейки
        HSSFCellStyle style = workbook.createCellStyle();
        // и применяем к этому стилю жирный шрифт
        style.setFont(font);
        style.setFillForegroundColor(color);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);

        if (!isMerged) {
            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setBottomBorderColor(HSSFColor.BLACK.index);
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setLeftBorderColor(HSSFColor.BLACK.index);
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setRightBorderColor(HSSFColor.BLACK.index);
            style.setBorderTop(CellStyle.BORDER_THIN);
            style.setTopBorderColor(HSSFColor.BLACK.index);
        }

        // проходим по всем ячейкам этой строки
        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
            // применяем созданный выше стиль к каждой ячейке
            row.getCell(j).setCellStyle(style);
        }
    }

    @FXML
    private void handleSaveExcelGroups() {
        // создание самого excel файла в памяти
        HSSFWorkbook workbook = new HSSFWorkbook();
        // создание листа с названием "Просто лист"
        for (int i = 0; i < mainApp.getSchedule().getActualGroups().size(); i++) {
            Group group = mainApp.getSchedule().getActualGroups().get(i);

            HSSFSheet sheet = workbook.createSheet(group.getName());

            // счетчик для строк
            int rowNum = 0;

            sheet.setColumnWidth(0, 11 * 256);
            sheet.setColumnWidth(1, 7 * 256);
            sheet.setColumnWidth(2, 40 * 256);
            sheet.setColumnWidth(3, 50 * 256);
            sheet.setColumnWidth(4, 16 * 256);

            // заполняем лист данными
            for (int j = 0; j < mainApp.getSchedule().getDays().size(); j++) {
                Day day = mainApp.getSchedule().getDays().get(j);

                // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(dates[j] + " " + day.getName());
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 4));
                setColumnStyle(row, HSSFColor.LIGHT_YELLOW.index, true);
                row = sheet.createRow(++rowNum);
                row.createCell(0).setCellValue("№ пары");
                row.createCell(1).setCellValue("Время");
                row.createCell(2).setCellValue("Дисциплина");
                row.createCell(3).setCellValue("Преподаватель");
                row.createCell(4).setCellValue("№ аудитории");
                setColumnStyle(row, HSSFColor.LIME.index, false);

                int k = rowNum;
                for (Lecture dataModel : mainApp.getSchedule().getDays().get(j).getLectures()) {
                    if (dataModel.getGroup().equals(group.getName()))
                        createSheetHeader(sheet, ++rowNum, dataModel, true);
                }

                if (k == rowNum) {
                    row = sheet.createRow(rowNum);
                    row.createCell(0).setCellValue(" День самоподготовки");
                    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 4));
                    setColumnStyle(row, HSSFColor.GOLD.index, true);

                    row = sheet.createRow(++rowNum);
                    row.createCell(0).setCellValue(" День самоподготовки");
                    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 4));
                    setColumnStyle(row, HSSFColor.GOLD.index, true);
                }
                ++rowNum;
            }
        }
        // записываем созданный в памяти Excel документ в файл
        File file = new File(selectDirectory().getAbsoluteFile() + "/Расписание для групп " + date + ".xls");
        try (FileOutputStream out = new FileOutputStream(file)) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Excel файл успешно создан!");
    }

    @FXML
    private void handleSaveExcelTeachers() {
        // создание самого excel файла в памяти
        HSSFWorkbook workbook = new HSSFWorkbook();
        // создание листа с названием "Просто лист"
        for (int i = 0; i < mainApp.getSchedule().getActualTeachers().size(); i++) {
            Teacher teacher = mainApp.getSchedule().getActualTeachers().get(i);

            HSSFSheet sheet = workbook.createSheet(teacher.getName());

            // счетчик для строк
            int rowNum = 0;

            sheet.setColumnWidth(0, 11 * 256);
            sheet.setColumnWidth(1, 7 * 256);
            sheet.setColumnWidth(2, 40 * 256);
            sheet.setColumnWidth(3, 50 * 256);
            sheet.setColumnWidth(4, 16 * 256);

            // заполняем лист данными
            for (int j = 0; j < mainApp.getSchedule().getDays().size(); j++) {
                Day day = mainApp.getSchedule().getDays().get(j);

                // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(dates[j] + " " + day.getName());
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 4));
                setColumnStyle(row, HSSFColor.LIGHT_YELLOW.index, true);
                row = sheet.createRow(++rowNum);
                row.createCell(0).setCellValue("№ пары");
                row.createCell(1).setCellValue("Время");
                row.createCell(2).setCellValue("Дисциплина");
                row.createCell(3).setCellValue("Группа");
                row.createCell(4).setCellValue("№ аудитории");
                setColumnStyle(row, HSSFColor.LIME.index, false);

                int k = rowNum;
                for (Lecture dataModel : mainApp.getSchedule().getDays().get(j).getLectures()) {
                    if (dataModel.getTeacher().equals(teacher.getName()))
                        createSheetHeader(sheet, ++rowNum, dataModel, false);
                }

                if (k == rowNum) {
                    row = sheet.createRow(rowNum);
                    row.createCell(0).setCellValue(" Выходной");
                    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 4));
                    setColumnStyle(row, HSSFColor.GOLD.index, true);

                    row = sheet.createRow(++rowNum);
                    row.createCell(0).setCellValue(" Выходной");
                    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 4));
                    setColumnStyle(row, HSSFColor.GOLD.index, true);
                }
                ++rowNum;
            }
        }
        // записываем созданный в памяти Excel документ в файл
        File file = new File(selectDirectory().getAbsoluteFile() + "/Расписание для преподавателей " + date + ".xls");
        try (FileOutputStream out = new FileOutputStream(file)) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Excel файл успешно создан!");
    }

    @FXML
    private void handleSaveExcelPrint() {

    }

    @FXML
    private void handleSaveExcelAll() {

    }

    private File selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        return directoryChooser.showDialog(mainApp.getPrimaryStage());
    }
}
