package ru.dkrpn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class WebElementList {
    private By selector;
    WebComponent parent;

    public WebElementList(WebComponent parent, By selector) {
        this.selector = selector;
        this.parent = parent;
    }

    public List<WebElement> findElements() {
        return parent.elements(selector);
    }
}
