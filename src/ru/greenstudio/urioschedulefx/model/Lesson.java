package ru.greenstudio.urioschedulefx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Lesson {
    private final StringProperty name;
    private final IntegerProperty hours;

    public Lesson() {
        this("", 0);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     * @param name
     * @param hours
     */
    public Lesson(String name, int hours) {
        this.name = new SimpleStringProperty(name);
        this.hours = new SimpleIntegerProperty(hours);
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

    public int getHours() {
        return hours.get();
    }

    public IntegerProperty getHoursProperty() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours.set(hours);
    }

    @Override
    public String toString() {
        return "name=" + name.get() + ", hours=" + hours.get();
    }
}
