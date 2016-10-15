package ru.greenstudio.urioschedulefx.Utils.IO;

import javafx.collections.ObservableList;
import ru.greenstudio.urioschedulefx.model.Group;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement(name = "groups")
public class GroupWrapper {

    List<Group> groups;

    @XmlElement(name = "group")
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}