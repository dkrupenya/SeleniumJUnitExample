package ru.dkrpn;

import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class WebComponentList<T extends WebComponent> {
    private By selector;
    WebComponent parent;
    Class<T> clazz;

    public WebComponentList(Class<T> clazz, WebComponent parent, By selector) {
        this.selector = selector;
        this.parent = parent;
        this.clazz = clazz;
    }

    public List<T> findElements() {
        return parent.elements(selector)
                .stream()
                .map(panel -> WebComponent.define(clazz, panel, parent))
                .collect(Collectors.toList());
    }
}
