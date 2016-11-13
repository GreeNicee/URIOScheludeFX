package ru.greenstudio.urioschedulefx.Utils.IO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cabs")
class CabWrapper {

    private List<String> cabs;

    @XmlElement(name = "cabs")
    List<String> getCabs() {
        return cabs;
    }

    void setCabs(List<String> cabs) {
        this.cabs = cabs;
    }
}