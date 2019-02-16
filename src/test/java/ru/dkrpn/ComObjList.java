package ru.dkrpn;

import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class ComObjList<T extends ComObj> {
    private By selector;
    ComObj parent;
    Class<T> clazz;

    public ComObjList(Class<T> clazz, ComObj parent, By selector) {
        this.selector = selector;
        this.parent = parent;
        this.clazz = clazz;
    }

    public List<T> findElements() {
        return parent.elements(selector)
                .stream()
                .map(panel -> ComObj.define(clazz, panel, parent))
                .collect(Collectors.toList());
    }
}
