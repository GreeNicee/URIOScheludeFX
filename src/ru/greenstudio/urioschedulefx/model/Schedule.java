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

    public Schedule(String name, List<Day> days, List<Lesson> maxGroupLessons, List<Lesson> maxTeacherLessons) {
        this.name = name;
        this.days = days;
        this.maxGroupLessons = maxGroupLessons;
        this.maxTeacherLessons = maxTeacherLessons;

        actualGroupLessons = new ArrayList<>();
        actualTeacherLessons = new ArrayList<>();
    }

    public Schedule() {
        this(null, null, null, null);
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

    @Override
    public String toString() {
        return "Schedule{" +
                "name='" + name + '\'' + "\n" +
                ", days=" + days +
                ",\n maxGroupLessons=" + maxGroupLessons +
                ",\n actualGroupLessons=" + actualGroupLessons +
                ",\n maxTeacherLessons=" + maxTeacherLessons +
                ",\n actualTeacherLessons=" + actualTeacherLessons +
                '}';
    }
}
