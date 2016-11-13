package ru.greenstudio.urioschedulefx.Utils.IO;

import ru.greenstudio.urioschedulefx.Utils.SaveableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "lecturesTime")
public class TimeScheduleWrapper {

    private List<String> lecturesData = new SaveableList();

    @XmlElement(name = "lectureTime")
    public List<String> getLecturesTimes() {
        return lecturesData;
    }

    public void setLecturesTime(List<String> lecturesData) {
        this.lecturesData = lecturesData;
    }
}