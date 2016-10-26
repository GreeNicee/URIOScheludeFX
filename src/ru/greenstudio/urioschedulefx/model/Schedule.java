package ru.greenstudio.urioschedulefx.model;

import javafx.collections.FXCollections;

import java.util.List;

public class Schedule {
    private String name;
    private List<Day> days;

    private List<Group> maxGroups;
    private List<Group> actualGroups;

    private List<Teacher> maxTeachers;
    private List<Teacher> actualTeachers;

    public Schedule(String name, List<Day> days) {
        this.name = name;
        this.days = days;
    }

    public Schedule() {
        this("", FXCollections.observableArrayList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }


    public List<Group> getMaxGroups() {
        return maxGroups;
    }

    public void setMaxGroups(List<Group> maxGroups) {
        this.maxGroups = maxGroups;
    }

    public List<Group> getActualGroups() {
        return actualGroups;
    }

    public void setActualGroups(List<Group> actualGroups) {
        this.actualGroups = actualGroups;
    }

    public List<Teacher> getMaxTeachers() {
        return maxTeachers;
    }

    public void setMaxTeachers(List<Teacher> maxTeachers) {
        this.maxTeachers = maxTeachers;
    }

    public List<Teacher> getActualTeachers() {
        return actualTeachers;
    }

    public void setActualTeachers(List<Teacher> actualTeachers) {
        this.actualTeachers = actualTeachers;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "name='" + name + '\'' + "\n" +
                ", days=" + days +
                ",\n maxGroups= " + maxGroups +
                ",\n actualGroups= " + actualGroups +
                ",\n maxTeachers= " + maxTeachers +
                ",\n actualTeachers= " + actualTeachers +
                '}';
    }
}
