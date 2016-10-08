package ru.greenstudio.urioschedulefx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cab {
    private final StringProperty name;

    public Cab() {
        this(null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     * @param name
     */
    public Cab(String name) {
        this.name = new SimpleStringProperty(name);
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
}