package ru.greenstudio.urioschedulefx.model;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private String name;
    private List<Day> days;

    private List<Lesson> maxGroupLessons;
    private List<Lesson> actualGroupLessons;

    private List<Lesson> maxTeacherLessons;
    private List<Lesson> actualTeacherLessons;

    private List<Group> maxGroups;
    private List<Group> actualGroups;

    private List<Teacher> maxTeachers;
    private List<Teacher> actualTeachers;

    public Schedule(String name, List<Day> days) {
        this.name = name;
        this.days = days;
        this.maxGroupLessons = maxGroupLessons;
        this.maxTeacherLessons = maxTeacherLessons;

        actualGroupLessons = new ArrayList<>();
        actualTeacherLessons = new ArrayList<>();
    }

    public Schedule() {
        this(null, null);
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

    public List<Lesson> getMaxGroupLessons() {
        return maxGroupLessons;
    }

    public void setMaxGroupLessons(List<Lesson> maxGroupLessons) {
        this.maxGroupLessons = maxGroupLessons;
    }

    public List<Lesson> getActualGroupLessons() {
        return actualGroupLessons;
    }

    public void setActualGroupLessons(List<Lesson> actualGroupLessons) {
        this.actualGroupLessons = actualGroupLessons;
    }

    public List<Lesson> getMaxTeacherLessons() {
        return maxTeacherLessons;
    }

    public void setMaxTeacherLessons(List<Lesson> maxTeacherLessons) {
        this.maxTeacherLessons = maxTeacherLessons;
    }

    public List<Lesson> getActualTeacherLessons() {
        return actualTeacherLessons;
    }

    public void setActualTeacherLessons(List<Lesson> actualTeacherLessons) {
        this.actualTeacherLessons = actualTeacherLessons;
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
                ",\n maxGroupLessons=" + maxGroupLessons +
                ",\n actualGroupLessons=" + actualGroupLessons +
                ",\n maxTeacherLessons=" + maxTeacherLessons +
                ",\n actualTeacherLessons=" + actualTeacherLessons +
                ",\n maxGroups= " + maxGroups +
                ",\n actualGroups= " + actualGroups +
                ",\n maxTeachers= " + maxTeachers +
                ",\n actualTeachers= " + actualTeachers +
                '}';
    }
}
