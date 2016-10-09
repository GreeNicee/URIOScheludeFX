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
import ru.greenstudio.urioschedulefx.view.GroupsLayoutController;
import ru.greenstudio.urioschedulefx.view.LessonsAndCabsController;

import java.io.IOException;
import java.util.Random;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * Данные, в виде наблюдаемого списка.
     */
    private ObservableList<String> lessonsListData = FXCollections.observableArrayList();
    private ObservableList<String> cabsListData = FXCollections.observableArrayList();

    private ObservableList<Lesson> lessonsData = FXCollections.observableArrayList();
    private ObservableList<Group> groupsData = FXCollections.observableArrayList();

    /**
     * Конструктор
     */
    public MainApp() {
        //TODO ИЗ ФАЙЛА ВСЕ!)
        // В качестве образца добавляем некоторые данные
        lessonsListData.add("Вася");
        lessonsListData.add("Володя");
        lessonsListData.add("Режий");
        lessonsListData.add("Валера");

        cabsListData.add("Валера");
        cabsListData.add("Эмили");
        cabsListData.add("Джон");
        cabsListData.add("Бъерн");

        Random random = new Random();//TODO Разобраться почему часы не рандомятся
        for (int i = 0; i < 3; i++) {
            lessonsData.clear();
            for (int j = 0; j < lessonsListData.size(); j++) {
                int rand = random.nextInt(100);
                lessonsData.add(new Lesson(lessonsListData.get(j), rand));
            }
            groupsData.add(new Group("" + i, lessonsData));
        }
        groupsData.add(new Group("Бизнес-информатика", lessonsData));

        for (int i = 0; i < groupsData.size(); i++) {
            System.out.println();
            for (int j = 0; j < groupsData.get(i).getLessons().size(); j++) {
                System.out.print(groupsData.get(i).getLessons().get(j).getName() + " " +
                        groupsData.get(i).getLessons().get(j).getHours() + " ");
            }
        }
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

    public ObservableList<Lesson> getLessonsData() {
        return lessonsData;
    }


    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("УРИО - составление расписания");
//            this.primaryStage.getIcons().add(new Image("file:resources/images/adress_book.png"));

        initRootLayout();

        showLessonsAndCabs();

        showGroups();
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
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/GroupsLayout.fxml"));
            SplitPane groups = loader.load();

            // Set person overview into the center of root layout.
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
            // Даём контроллеру доступ к главному приложению.
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
