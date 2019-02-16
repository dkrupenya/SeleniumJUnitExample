package ru.dkrpn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ComponentObject {
    WebDriver driver;
    By selector;
    WebElement rootElement;
    ComponentObject parent;

    public ComponentObject(By selector, WebDriver driver) {
        this.driver = driver;
        this.selector = selector;
        this.rootElement = driver.findElement(this.selector);
        this.parent = null;
    }

    public ComponentObject(By selector, ComponentObject parent) {
        this.driver = parent.driver;
        this.selector = selector;
        this.rootElement = WebElementProxy.define(this.selector, parent.rootElement, false);
        this.parent = parent;
    }
}
