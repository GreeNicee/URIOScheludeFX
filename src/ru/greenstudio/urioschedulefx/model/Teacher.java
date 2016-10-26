package ru.greenstudio.urioschedulefx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Teacher {
    private final StringProperty name;
    @XmlElement(name = "lessons")
    private final List<Lesson> lessons;

//    @XmlElement(name = "lesson_name")
//    private final List<String> lessonsNames;
//    @XmlElement(name = "lesson_hours")
//    private final List<Integer> lessonsHours;

    public Teacher() {
        this("", FXCollections.observableArrayList());
    }

    public Teacher(String name, List<Lesson> lessons) {
        this.name = new SimpleStringProperty(name);
        this.lessons = lessons;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public String toString() {
        return "\nTeacher{" +
                "name=" + name +
                ", lessons=" + lessons +
                '}';
    }
}
