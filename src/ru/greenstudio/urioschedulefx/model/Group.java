package ru.greenstudio.urioschedulefx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Group {
    private final StringProperty name;
    @XmlElement(name = "lesson_hours")
    private final List<Integer> lessonsHours;
    private final List<String> lessonsNames;

    public Group() {
        this("", null, FXCollections.observableArrayList());
    }

    public List<String> getLessonsNames() {
        return lessonsNames;
    }

    public Group(String name, List<String> lessonsNames, List<Integer> lessonsHours) {
        this.name = new SimpleStringProperty(name);

        this.lessonsHours = lessonsHours;

        this.lessonsNames = lessonsNames;

    }

    public List<Integer> getNullHours() {
        List<Integer> list = FXCollections.observableArrayList();
        for (int i = 0; i < this.getLessonsHours().size(); i++) {
            list.add(0);
        }
        return list;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public List<Integer> getLessonsHours() {
        return lessonsHours;
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return "\nGroup{" +
                "name=" + name +
                ", lessonsHours=" + lessonsHours +
                '}';
    }
}
