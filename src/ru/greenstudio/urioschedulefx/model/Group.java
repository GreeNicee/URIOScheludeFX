package ru.greenstudio.urioschedulefx.model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class Group {
    private final StringProperty name;
    private final ObservableList<Lesson> lessons;

    public Group() {
        this(null,null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     * @param name
     * @param lessons
     */
    public Group(String name, ObservableList<Lesson> lessons) {
        this.name = new SimpleStringProperty(name);
        this.lessons = new SimpleListProperty<Lesson>(lessons);
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

    public ObservableList<Lesson> getLessons() {
        return lessons;
    }
    public void setLessons(ObservableList<Lesson> lessons){
        this.lessons.setAll(lessons);
    }
}
