package ru.greenstudio.urioschedulefx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.greenstudio.urioschedulefx.model.Group;
import ru.greenstudio.urioschedulefx.model.Lesson;
import ru.greenstudio.urioschedulefx.model.Teacher;
import ru.greenstudio.urioschedulefx.view.GroupsLayoutController;
import ru.greenstudio.urioschedulefx.view.LessonsAndCabsController;
import ru.greenstudio.urioschedulefx.view.TeachersLayoutController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.greenstudio.urioschedulefx.Utils.IO.Files.loadDataFromFile;
import static ru.greenstudio.urioschedulefx.Utils.IO.Files.saveDataToFile;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<String> lessonsListData = FXCollections.observableArrayList();
    private ObservableList<String> cabsListData = FXCollections.observableArrayList();

    private ObservableList<Group> groupsData = FXCollections.observableArrayList();

    private ObservableList<Lesson> maxLessonsData = FXCollections.observableArrayList();
    private ObservableList<Lesson> lessonsData = FXCollections.observableArrayList();
    private ObservableList<Teacher> teachersData = FXCollections.observableArrayList();

    public MainApp() {
        lessonsListData.setAll("LOL");
        cabsListData.setAll("LOL");
        groupsData.setAll(new Group("LOL",
                FXCollections.observableArrayList("lol", "lol"),
                FXCollections.observableArrayList(0, 0)));
        Teacher teacher = new Teacher("писька", new ArrayList<Lesson>());
        teacher.getLessons().add(0, new Lesson("asd", 12));
        teacher.getLessons().add(0, new Lesson("dsa", 0));

        teachersData.setAll(teacher);

        lessonsListData.clear();
        cabsListData.clear();
        groupsData.clear();
        teachersData.clear();

//        setTestValues();

        loadDataFromFile(lessonsListData, cabsListData, groupsData, teachersData);


        for (int i = 0; i < teachersData.size(); i++) {
            if (teachersData.get(i).getLessons() == null) {
                teachersData.set(i, new Teacher(teachersData.get(i).getName(), FXCollections.observableArrayList()));
            }
        }

        setMaxLessonsData();
        setLessonsData();

        System.out.println("MaxLessonsData");
        for (Lesson aLessonsData : maxLessonsData) {
            System.out.println(aLessonsData.getName() + " " + aLessonsData.getHours());
        }
        System.out.println("LessonsData");
        for (Lesson aLessonsData : lessonsData) {
            System.out.println(aLessonsData.getName() + " " + aLessonsData.getHours());
        }
    }

    private void setMaxLessonsData() {
        for (Group aGroupsData : groupsData) {
            List<String> lessNames = lessonsListData;
            for (int j = 0; j < lessNames.size(); j++) {
                String less = lessNames.get(j);
                int lessHours = aGroupsData.getLessonsHours().get(j);

                if (lessHours != 0) {
                    if (maxLessonsData.size() == 0) {
                        System.out.println("null " + less + " " + lessHours);
                        maxLessonsData.add(new Lesson(less, lessHours));
                    } else {
                        for (int i = 0; i < maxLessonsData.size(); i++) {
                            if (maxLessonsData.get(i).getName().equals(less)) {
                                int num = maxLessonsData.get(i).getHours() + lessHours;
                                maxLessonsData.get(i).setHours(num);
                                break;
                            } else if (i == maxLessonsData.size() - 1) {
                                maxLessonsData.add(new Lesson(less, lessHours));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void setLessonsData() {
        for (Lesson aMaxLessonsData : maxLessonsData) {
            lessonsData.add(new Lesson(aMaxLessonsData.getName(), 0));
        }
        for (Teacher aTeachersData : teachersData) {
            List<Lesson> lessons = aTeachersData.getLessons();
            if (lessons != null) {
                for (Lesson lesson : lessons) {
                    if (lesson.getHours() != 0) {
                        for (Lesson aLessonsData : lessonsData) {
                            if (aLessonsData.getName().equals(lesson.getName())) {
                                int num = aLessonsData.getHours() + lesson.getHours();
                                aLessonsData.setHours(num);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void setTestValues() {
        // В качестве образца добавляем некоторые данные
        lessonsListData.add("Вася");
        lessonsListData.add("Володя");
        lessonsListData.add("Режий");
        lessonsListData.add("Валера");

        cabsListData.add("Валера");
        cabsListData.add("Эмили");
        cabsListData.add("Джон");
        cabsListData.add("Бъерн");

        Random random = new Random();
        ObservableList<Integer> lessHoursData = FXCollections.observableArrayList();
        for (int i = 0; i < 3; i++) {
            lessHoursData.clear();
            for (int j = 0; j < lessonsListData.size(); j++) {
                int rand = random.nextInt(100);
                lessHoursData.add(rand);
            }
            Group group = new Group("" + i, lessonsListData, FXCollections.observableArrayList(lessHoursData));
            groupsData.add(group);
//            System.out.println(group.lessons);
        }

        groupsData.add(new Group("Бизнес-информатика", lessonsListData, lessHoursData));
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

    public ObservableList<Lesson> getMaxLessonsData() {
        return maxLessonsData;
    }

    public ObservableList<Lesson> getLessonsData() {
        return lessonsData;
    }

    public ObservableList<Teacher> getTeachersData() {
        return teachersData;
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("УРИО - составление расписания");
//            this.primaryStage.getIcons().add(new Image("file:resources/images/adress_book.png"));

        initRootLayout();

        showLessonsAndCabs();

        showGroups();

        showTeachers();
    }

    @Override
    public void stop() {
        System.out.println("Stage is closing");
        saveDataToFile(lessonsListData, cabsListData, groupsData, teachersData);
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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
