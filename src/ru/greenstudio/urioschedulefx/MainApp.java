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
import ru.greenstudio.urioschedulefx.model.Lesson;
import ru.greenstudio.urioschedulefx.view.LessonsAndCabsController;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * Данные, в виде наблюдаемого списка.
     */
    private ObservableList<String> lessonsData = FXCollections.observableArrayList();
    private ObservableList<String> cabsData = FXCollections.observableArrayList();

    /**
     * Конструктор
     */
    public MainApp() {
        // В качестве образца добавляем некоторые данные
        lessonsData.add(new Lesson("Вася").getName());
        lessonsData.add(new Lesson("Володя").getName());
        lessonsData.add(new Lesson("Режий").getName());
        lessonsData.add(new Lesson("Валера").getName());

        cabsData.add(new Lesson("Валера").getName());
        cabsData.add(new Lesson("Эмили").getName());
        cabsData.add(new Lesson("Джон").getName());
        cabsData.add(new Lesson("Бъерн").getName());
    }

    public ObservableList<String> getLessonsData() {
        return lessonsData;
    }
    public ObservableList<String> getCabsData() {
        return cabsData;
    }


    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("УРИО - составление расписания");
//            this.primaryStage.getIcons().add(new Image("file:resources/images/adress_book.png"));

        initRootLayout();

        showLessonsAndCabs();
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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
