package ru.greenstudio.urioschedulefx.Utils;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Window;

public class Alerts {
    public static void showWarningOperation(Window window, String operation, String item) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(window);
        alert.setTitle("Ничего не выбрано");
        alert.setHeaderText("Нельзя " + operation + " то, чего нет=)");
        alert.setContentText("Выберите " + item);

        alert.showAndWait();
    }

    public static boolean alreadyInStringData(ObservableList<String> data, String name,Window window,String title) {
        for (String aData : data) {
            if (aData.equals(name)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(window);
                alert.setTitle("Такой "+title+" уже есть!");
                alert.setHeaderText("Такой "+title+" уже есть!");
                alert.setContentText("Выберите другое название для \""+name+"\"");

                alert.showAndWait();
                return true;
            }
        }
        return false;
    }
}
