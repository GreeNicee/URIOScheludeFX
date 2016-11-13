package ru.greenstudio.urioschedulefx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.greenstudio.urioschedulefx.Utils.MaskField;
import ru.greenstudio.urioschedulefx.model.*;
import ru.greenstudio.urioschedulefx.view.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.greenstudio.urioschedulefx.Utils.IO.Files.*;

public class MainApp extends Application {
    private ChoiceBox<String> comboGroups;

    public ChoiceBox<String> getComboGroups() {
        return comboGroups;
    }

    public void setComboGroups(ChoiceBox<String> comboGroups) {
        this.comboGroups = comboGroups;
    }

    private ArrayList<MaskField> lecturesData = new ArrayList<>(8);

    public ArrayList<MaskField> getLecturesData() {
        return lecturesData;
    }

    public void setLecturesData(ArrayList<MaskField> lecturesData) {
        this.lecturesData = lecturesData;
    }

    private TableView<Lesson> lessonTableView;

    public TableView<Lesson> getLessonTableView() {
        return lessonTableView;
    }

    public void setLessonTableView(TableView<Lesson> lessonTableView) {
        this.lessonTableView = lessonTableView;
    }

    private TableView<Lesson> teacherLessonTableView;

    public TableView<Lesson> getTeacherLessonTableView() {
        return teacherLessonTableView;
    }

    public void setTeacherLessonTableView(TableView<Lesson> teacherLessonTableView) {
        this.teacherLessonTableView = teacherLessonTableView;
    }

    private Stage primaryStage;
    private BorderPane rootLayout;

    private Schedule schedule;

    public Schedule getSchedule() {
        return schedule;
    }

    private ObservableList<String> lessonsListData = FXCollections.observableArrayList();
    private ObservableList<String> cabsListData = FXCollections.observableArrayList();

    private ObservableList<Group> groupsData = FXCollections.observableArrayList();

    private ObservableList<Teacher> teachersData = FXCollections.observableArrayList();


    public MainApp() {
        lessonsListData.setAll("LOL");
        cabsListData.setAll("LOL");
        groupsData.setAll(new Group("LOL",
                FXCollections.observableArrayList("lol", "lol"),
                FXCollections.observableArrayList(0, 0)));
        Teacher teacher = new Teacher("df", new ArrayList<>());
        teacher.getLessons().add(0, new Lesson("asd", 12));
        teacher.getLessons().add(0, new Lesson("dsa", 0));

        teachersData.setAll(teacher);

        lessonsListData.clear();
        cabsListData.clear();
        groupsData.clear();
        teachersData.clear();

        loadDataFromFile(lessonsListData, cabsListData, groupsData, teachersData);

        schedule = new Schedule("Тестовое расписание", FXCollections.observableArrayList());
        schedule.getDays().add(new Day("Понедельник", new ArrayList<>()));
        schedule.getDays().add(new Day("Вторник", new ArrayList<>()));
        schedule.getDays().add(new Day("Среда", new ArrayList<>()));
        schedule.getDays().add(new Day("Четверг", new ArrayList<>()));
        schedule.getDays().add(new Day("Пятница", new ArrayList<>()));
        schedule.getDays().add(new Day("Суббота", new ArrayList<>()));
        schedule.getDays().add(new Day("Воскресенье", new ArrayList<>()));
        schedule.setMaxGroups(groupsData);
        schedule.setActualGroups(FXCollections.observableArrayList());

        schedule.setMaxTeachers(teachersData);
        schedule.setActualTeachers(FXCollections.observableArrayList());

        loadSchedule(schedule);
    }

    public ObservableList<String> getLessonsListData() {
        return lessonsListData;
    }

    public ObservableList<String> getCabsListData() {
        return cabsListData;
    }

    public ObservableList<Group> getGroupsData() {
        return groupsData;
    }

    public ObservableList<Teacher> getTeachersData() {
        return teachersData;
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setMaximized(true);
        this.primaryStage.setTitle("УРИО - составление расписания");
//            this.primaryStage.getIcons().add(new Image("file:resources/images/adress_book.png"));

        initRootLayout();

        showLessonsAndCabs();

        showGroups();

        showTeachers();

        showSchedule();

        showTimeSchedule();
    }

    @Override
    public void stop() {
        System.out.println("Stage is closing");
        saveDataToFile(lessonsListData, cabsListData, groupsData, teachersData, schedule);
        List<String> list = new ArrayList<>(8);
        list.addAll(lecturesData.stream().map(MaskField::getPlainText).collect(Collectors.toList()));
        saveLecturesTime(list);
    }

    private void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            primaryStage.show();
            // Даём контроллеру доступ к главному приложению.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showTeachers() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TeachersLayout.fxml"));
            SplitPane teachers = loader.load();

            for (int i = 0; i < rootLayout.getChildren().size(); i++) {
                ObservableList<Node> rootChilds = rootLayout.getChildren();
                if (rootChilds.get(i) instanceof TabPane) {
                    ObservableList<Tab> rootTabs = ((TabPane) rootChilds.get(i)).getTabs();
                    for (Tab rootTab : rootTabs) {
                        String tabId = "";
                        if (rootTab.getId() != null)
                            tabId = rootTab.getId();
                        if (tabId.equals("tabTeachers")) {
                            AnchorPane anchorPane = (AnchorPane) rootTab.getContent();
                            anchorPane.getChildren().add(teachers);

                            AnchorPane.setTopAnchor(teachers, 0.0);
                            AnchorPane.setBottomAnchor(teachers, 0.0);
                            AnchorPane.setLeftAnchor(teachers, 0.0);
                            AnchorPane.setRightAnchor(teachers, 0.0);
                        }
                    }
                }
            }
            // Даём контроллеру доступ к главному приложению.
            TeachersLayoutController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSchedule() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ScheduleLayout.fxml"));
            AnchorPane schedule = loader.load();

            for (int i = 0; i < rootLayout.getChildren().size(); i++) {
                ObservableList<Node> rootChilds = rootLayout.getChildren();
                if (rootChilds.get(i) instanceof TabPane) {
                    ObservableList<Tab> rootTabs = ((TabPane) rootChilds.get(i)).getTabs();
                    for (Tab rootTab : rootTabs) {
                        String tabId = "";
                        if (rootTab.getId() != null)
                            tabId = rootTab.getId();
                        if (tabId.equals("tabSchedule")) {
                            AnchorPane anchorPane = (AnchorPane) rootTab.getContent();
                            anchorPane.getChildren().add(schedule);

                            AnchorPane.setTopAnchor(schedule, 0.0);
                            AnchorPane.setBottomAnchor(schedule, 0.0);
                            AnchorPane.setLeftAnchor(schedule, 0.0);
                            AnchorPane.setRightAnchor(schedule, 0.0);
                        }
                    }
                }
            }
            // Даём контроллеру доступ к главному приложению.
            ScheduleLayoutController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showLessonsAndCabs() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LessonsAndCabsLayout.fxml"));
            SplitPane lessAndCabs = loader.load();

            // Set person overview into the center of root layout.
            for (int i = 0; i < rootLayout.getChildren().size(); i++) {
                ObservableList<Node> rootChilds = rootLayout.getChildren();
                if (rootChilds.get(i) instanceof TabPane) {
                    ObservableList<Tab> rootTabs = ((TabPane) rootChilds.get(i)).getTabs();
                    for (Tab rootTab : rootTabs) {
                        String tabId = "";
                        if (rootTab.getId() != null)
                            tabId = rootTab.getId();
                        if (tabId.equals("tabLessonsAndCabs")) {
                            AnchorPane anchorPane = (AnchorPane) rootTab.getContent();
                            anchorPane.getChildren().add(lessAndCabs);

                            AnchorPane.setTopAnchor(lessAndCabs, 0.0);
                            AnchorPane.setBottomAnchor(lessAndCabs, 0.0);
                            AnchorPane.setLeftAnchor(lessAndCabs, 0.0);
                            AnchorPane.setRightAnchor(lessAndCabs, 0.0);
                        }
                    }
                }
            }
            // Даём контроллеру доступ к главному приложению.
            LessonsAndCabsController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showGroups() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/GroupsLayout.fxml"));
            SplitPane groups = loader.load();

            for (int i = 0; i < rootLayout.getChildren().size(); i++) {
                ObservableList<Node> rootChilds = rootLayout.getChildren();
                if (rootChilds.get(i) instanceof TabPane) {
                    ObservableList<Tab> rootTabs = ((TabPane) rootChilds.get(i)).getTabs();
                    for (Tab rootTab : rootTabs) {
                        String tabId = "";
                        if (rootTab.getId() != null)
                            tabId = rootTab.getId();
                        if (tabId.equals("tabGroups")) {
                            AnchorPane anchorPane = (AnchorPane) rootTab.getContent();
                            anchorPane.getChildren().add(groups);

                            AnchorPane.setTopAnchor(groups, 0.0);
                            AnchorPane.setBottomAnchor(groups, 0.0);
                            AnchorPane.setLeftAnchor(groups, 0.0);
                            AnchorPane.setRightAnchor(groups, 0.0);
                        }
                    }
                }
            }
            GroupsLayoutController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showTimeSchedule() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TimeScheduleLayout.fxml"));
            AnchorPane timeSchedule = loader.load();

            for (int i = 0; i < rootLayout.getChildren().size(); i++) {
                ObservableList<Node> rootChilds = rootLayout.getChildren();
                if (rootChilds.get(i) instanceof TabPane) {
                    ObservableList<Tab> rootTabs = ((TabPane) rootChilds.get(i)).getTabs();
                    for (Tab rootTab : rootTabs) {
                        String tabId = "";
                        if (rootTab.getId() != null)
                            tabId = rootTab.getId();
                        if (tabId.equals("tabTimeSchedule")) {
                            AnchorPane anchorPane = (AnchorPane) rootTab.getContent();
                            anchorPane.getChildren().add(timeSchedule);

                            AnchorPane.setTopAnchor(timeSchedule, 0.0);
                            AnchorPane.setBottomAnchor(timeSchedule, 0.0);
                            AnchorPane.setLeftAnchor(timeSchedule, 0.0);
                            AnchorPane.setRightAnchor(timeSchedule, 0.0);
                        }
                    }
                }
            }
            TimeScheduleLayoutController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
