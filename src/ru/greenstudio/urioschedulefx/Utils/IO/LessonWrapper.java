package ru.greenstudio.urioschedulefx.Utils.IO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "lessons")
class LessonWrapper {

    private List<String> lessons;

    @XmlElement(name = "lesson")
    List<String> getLessons() {
        return lessons;
    }

    void setLessons(List<String> lessons) {
        this.lessons = lessons;
    }
}