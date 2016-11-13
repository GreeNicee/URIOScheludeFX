package ru.greenstudio.urioschedulefx.Utils.IO;

import ru.greenstudio.urioschedulefx.model.Teacher;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "teachers")
class TeacherWrapper {

    private List<Teacher> teachers;

    @XmlElement(name = "teacher")
    List<Teacher> getTeachers() {
        return teachers;
    }

    void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}