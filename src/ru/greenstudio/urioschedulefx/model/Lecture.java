package ru.greenstudio.urioschedulefx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlElement;

public class Lecture {
    private int numName;

    private String group;
    private String teacher;
    private String lesson;
    private String cab;

    public Lecture() {
        this(0, null, null, null, null);
    }

    public Lecture(int numName, String group, String teacher, String lesson, String cab) {
        this.numName = numName;
        this.group = group;
        this.teacher = teacher;
        this.lesson = lesson;
        this.cab = cab;
    }

    public void setNumName(int numName) {
        this.numName = numName;
    }

    @XmlElement(name = "numName")
    public int getNumName() {
        return numName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @XmlElement(name = "lesson")
    public String getLesson() {
        return lesson;
    }

    public IntegerProperty numNameProperty() {
        return new SimpleIntegerProperty(numName);
    }

    public StringProperty teacherProperty() {
        return new SimpleStringProperty(teacher);
    }

    public StringProperty lessonProperty() {
        return new SimpleStringProperty(lesson);
    }

    public StringProperty cabProperty() {
        return new SimpleStringProperty(cab);
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    @XmlElement(name = "cab")
    public String getCab() {
        return cab;
    }

    public void setCab(String cab) {
        this.cab = cab;
    }

    @Override
    public String toString() {
        return "\nLecture{" +
                "numName=" + numName +
                ", group='" + group + '\'' +
                ", teacher='" + teacher + '\'' +
                ", lesson=" + lesson +
                ", cab=" + cab +
                '}';
    }
}