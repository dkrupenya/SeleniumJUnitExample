package ru.dkrpn;

import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class WebComponentList<T extends WebComponent> {
    private By locator;
    WebComponent parent;
    Class<T> clazz;

    public WebComponentList(Class<T> clazz, WebComponent parent, By locator) {
        this.locator = locator;
        this.parent = parent;
        this.clazz = clazz;
    }

    public List<T> findElements() {
        return parent.realElements(locator)
                .stream()
                .map(panel -> WebComponent.define(clazz, panel, parent))
                .collect(Collectors.toList());
    }
}
