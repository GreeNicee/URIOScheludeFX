package ru.greenstudio.urioschedulefx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Teacher {
    private final StringProperty name;
    @XmlElement(name = "lesson_name")
    private final List<String> lessonsNames;
    @XmlElement(name = "lesson_hours")
    private final List<Integer> lessonsHours;

    public Teacher() {
        this(null, null, null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     * @param name
     * @param lessonsNames
     * @param lessonsHours
     */
    public Teacher(String name, ObservableList<String> lessonsNames, ObservableList<Integer> lessonsHours) {
        this.name = new SimpleStringProperty(name);

        this.lessonsNames = lessonsNames;
        this.lessonsHours = lessonsHours;
    }
}
