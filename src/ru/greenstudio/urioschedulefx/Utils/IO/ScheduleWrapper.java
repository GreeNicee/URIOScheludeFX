package ru.greenstudio.urioschedulefx.Utils.IO;

import ru.greenstudio.urioschedulefx.model.Schedule;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "schedule")
class ScheduleWrapper {

    private Schedule schedule;

    @XmlElement(name = "schedule")
    Schedule getSchedule() {
        return schedule;
    }

    void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}