package ru.greenstudio.urioschedulefx.Utils;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Window;

public class Alerts {
    public static void showWarningOperation(String operation, Window window, String item) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(window);
        alert.setTitle("Ничего не выбрано");
        alert.setHeaderText("Нельзя " + operation + "ить то, чего нет=)");
        String contentText = "Выберите " + item + " для " + operation;
        if (operation.equals("добав"))
            contentText = contentText + "ления";
        else
            contentText = contentText + "ения";

        alert.setContentText(contentText);

        alert.showAndWait();
    }

    public static boolean alreadyInStringData(ObservableList<String> data, String name,Window window) {
        for (String aData : data) {
            if (aData.equals(name)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(window);
                alert.setTitle("Такой "+name+" уже есть!");
                alert.setHeaderText("Такой "+name+" уже есть!");
                alert.setContentText("Выберите другое название для \""+name+"\"");

                alert.showAndWait();
                return true;
            }
        }
        return false;
    }
}
