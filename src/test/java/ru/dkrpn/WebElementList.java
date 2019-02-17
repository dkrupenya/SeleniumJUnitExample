package ru.dkrpn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class WebElementList {
    private By locator;
    WebComponent parent;

    public WebElementList(WebComponent parent, By locator) {
        this.locator = locator;
        this.parent = parent;
    }

    public List<WebElement> findElements() {
        return parent.elements(locator);
    }
}
