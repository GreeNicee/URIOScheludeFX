package ru.greenstudio.urioschedulefx.Utils.IO;

import javafx.scene.control.TableView;
import ru.greenstudio.urioschedulefx.model.Group;
import ru.greenstudio.urioschedulefx.model.Lesson;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "groups")
public class GroupWrapper {

    private List<Group> groups;

    @XmlElement(name = "group")
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}