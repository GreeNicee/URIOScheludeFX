package ru.greenstudio.urioschedulefx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Group {
    private final StringProperty name;
    private final ObservableList<String> lessonsNames;
    private final ObservableList<Integer> lessonsHours;

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
    public Group(String name, ObservableList<String> lessonsNames, ObservableList<Integer> lessonsHours) {
        this.name = new SimpleStringProperty(name);
//        this.lessons = new SimpleListProperty<Lesson>(lessons);

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

    public ObservableList<String> getLessonsNames() {
        return lessonsNames;
    }

    public ObservableList<Integer> getLessonsHours() {
        return lessonsHours;
    }

    public StringProperty nameProperty() {
        return name;
    }

}
