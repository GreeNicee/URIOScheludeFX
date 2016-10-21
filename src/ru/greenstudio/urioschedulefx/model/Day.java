package ru.greenstudio.urioschedulefx.model;

import java.util.List;

public class Day {
    private final String name;
    private List<Lecture> lectures;

    public Day() {
        this(null, null);
    }

    public Day(String name, List<Lecture> lectures) {
        this.name = name;
        this.lectures = lectures;
    }

    public String getName() {
        return name;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public String toString() {
        return "\nDay{" +
                "name='" + name + '\'' +
                ", lectures=" + lectures +
                '}';
    }

}