package ru.greenstudio.urioschedulefx.Utils.IO;

import ru.greenstudio.urioschedulefx.model.Schedule;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "schedule")
public class ScheduleWrapper {

    Schedule schedule;

    @XmlElement(name = "scheduledsfsdf")
    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}