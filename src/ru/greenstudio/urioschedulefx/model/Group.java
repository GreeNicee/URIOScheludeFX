package ru.greenstudio.urioschedulefx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Group {
    private final StringProperty name;
    @XmlElement(name = "lesson_name")
    private final List<String> lessonsNames;
    @XmlElement(name = "lesson_hours")
    private final List<Integer> lessonsHours;

    public Group() {
        this(null, null, null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     * @param name
     * @param lessonsNames
     * @param lessonsHours
     */
    public Group(String name, List<String> lessonsNames, List<Integer> lessonsHours) {
        this.name = new SimpleStringProperty(name);

        this.lessonsNames = lessonsNames;
        this.lessonsHours = lessonsHours;
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

    public List<String> getLessonsNames() {
        return lessonsNames;
    }

    public List<Integer> getLessonsHours() {
        return lessonsHours;
    }

    public StringProperty nameProperty() {
        return name;
    }

}
