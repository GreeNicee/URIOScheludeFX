package ru.greenstudio.urioschedulefx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

public class Lecture {
    private IntegerProperty numName;

    private String group;
    private String teacher;
    private StringProperty lesson;
    private StringProperty cab;

    public Lecture() {
        this(new SimpleIntegerProperty(), null, null, null, null);
    }

    public Lecture(IntegerProperty numName, String group, String teacher, StringProperty lesson, StringProperty cab) {
        this.numName = numName;
        this.group = group;
        this.teacher = teacher;
        this.lesson = lesson;
        this.cab = cab;
    }

    public void setNumName(IntegerProperty numName) {
        this.numName = numName;
    }

    public IntegerProperty getNumName() {
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

    public StringProperty getLesson() {
        return lesson;
    }

    public void setLesson(StringProperty lesson) {
        this.lesson = lesson;
    }

    public StringProperty getCab() {
        return cab;
    }

    public void setCab(StringProperty cab) {
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