package ru.greenstudio.urioschedulefx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Lesson {
    private final StringProperty name;

    public Lesson() {
        this(null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     * @param name
     */
    public Lesson(String name) {

        this.name = new SimpleStringProperty(name);
    }

    public String getName() {

        return name.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setName(String name){
            this.name.set(name);
    }
}
