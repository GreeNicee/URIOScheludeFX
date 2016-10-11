package ru.greenstudio.urioschedulefx.model;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group {
    private final StringProperty name;
//    private final ObservableList<Lesson> lessons;
    private final ObservableList<String> lessonsNames;
    private final ObservableList<Integer> lessonsHours;

    public Group() {
        this(null, null,null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     * @param name
     * @param lessons
     * @param lessonsNames
     * @param lessonsHours
     */
    public Group(String name, ObservableList<String> lessonsNames,ObservableList<Integer> lessonsHours) {
        this.name = new SimpleStringProperty(name);
//        this.lessons = new SimpleListProperty<Lesson>(lessons);

        this.lessonsNames = FXCollections.observableArrayList();
        this.lessonsHours = FXCollections.observableArrayList();
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

    public ObservableList<String> getLessonsNames() {
        return lessonsNames;
    }

    public ObservableList<Integer> getLessonsHours() {
        return lessonsHours;
    }
//    public ObservableList<Lesson> getLessons() {
//        return lessons;
//    }

//    public void setLessons(ObservableList<Lesson> lessons) {
//        this.lessons.setAll(lessons);
//    }
}
