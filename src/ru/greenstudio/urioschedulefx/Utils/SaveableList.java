package ru.greenstudio.urioschedulefx.Utils;

import javax.xml.bind.annotation.XmlElement;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SaveableList extends AbstractList<String> {

    @XmlElement(name = "e")
    private final List<String> list = new ArrayList<>();

    @Override
    public String get(int index) {
        return list.get(index);
    }

    @Override
    public boolean add(String e) {
        return list.add(e);
    }

    @Override
    public int size() {
        return list.size();
    }

    // You might want to implement/override other methods which you use
    // And is not provided by AbstractList

}