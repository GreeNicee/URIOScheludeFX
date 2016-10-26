package ru.greenstudio.urioschedulefx.model;

import javafx.collections.FXCollections;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "day")
public class Day {
    @XmlElement(name = "name")
    private final String name;
    private List<Lecture> lectures;

    public Day() {
        this("", FXCollections.observableArrayList());
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