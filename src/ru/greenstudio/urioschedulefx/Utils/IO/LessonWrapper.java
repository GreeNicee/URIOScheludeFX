package ru.greenstudio.urioschedulefx.Utils.IO;

import ru.greenstudio.urioschedulefx.model.Lesson;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "lessons")
public class LessonWrapper {

    private List<String> lessons;

    @XmlElement(name = "lesson")
    public List<String> getLessons() {
        return lessons;
    }

    public void setLessons(List<String> lessons) {
        this.lessons = lessons;
    }
}