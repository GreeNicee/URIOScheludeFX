package ru.greenstudio.urioschedulefx.Utils.IO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cabs")
public class CabWrapper {

    private List<String> cabs;

    @XmlElement(name = "cabs")
    public List<String> getCabs() {
        return cabs;
    }

    public void setCabs(List<String> cabs) {
        this.cabs = cabs;
    }
}